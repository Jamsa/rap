package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.core.security.jwt.JwtToken;
import com.github.jamsa.rap.core.util.TokenUtil;
import com.github.jamsa.rap.model.LoginUser;
import com.github.jamsa.rap.model.ResponseData;
import com.github.jamsa.rap.model.User;
import com.github.jamsa.rap.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhujie on 16/7/4.
 */
@RestController
public class MainController {
    public static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UserService userService;

    @Autowired
    TokenUtil tokenUtil;


    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginUser loginUser, HttpServletResponse response, Device device) throws IOException{
        String username =loginUser.getUsername();
        String password = loginUser.getPassword();

        //TODO: 进行登录

        // 验证用户名密码成功后生成token
        String token = tokenUtil.generateToken(username, device);
        // 构建JwtToken
        JwtToken jwtToken = new JwtToken();
        jwtToken.setPrincipal(username);
        jwtToken.setToken(token);

        Subject subject = SecurityUtils.getSubject();
        ResponseData responseData = ResponseData.error("用户名或密码错误");
        try {
            // 该方法会调用JwtRealm中的doGetAuthenticationInfo方法
            subject.login(jwtToken);
        } catch (UnknownAccountException exception) {
            logger.error("账号不存在",exception);
            responseData.setData("帐号不存在");
        } catch (IncorrectCredentialsException exception) {
            logger.error("错误的凭证，用户名或密码不正确",exception);
            responseData.setData("错误的凭证，用户名或密码不正确");
        } catch (LockedAccountException exception) {
            logger.error("账户已锁定",exception);
            responseData.setData("账户已锁定");
        } catch (ExcessiveAttemptsException exception) {
            logger.error("错误次数过多",exception);
            responseData.setData("错误次数过多");
        } catch (AuthenticationException exception) {
            logger.error("认证失败",exception);
            responseData.setData("认证失败");
        }

        // 认证通过
        if(subject.isAuthenticated()){
            // 将token写出到cookie
            Cookie cookie =new Cookie("token",token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600 * 5);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.flushBuffer();

            return ResponseData.success(token);
            //return ResponseEntity.ok(responseData);
            //return responseData;
            /*
            jsonObject.put("code",200);
            jsonObject.put("msg","success");
            jsonObject.put("token",token);
            jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
            return jsonObject;
            return token;*/
        }else{
            //ResponseData<String> responseData = new ResponseData<>();
            //responseData.setCode(403);
            //responseData.setMessage("error");
            return responseData;
            /*jsonObject.put("code",403);
            jsonObject.put("msg","error");
            jsonObject.put("timestamp", Calendar.getInstance().getTimeInMillis());
            return jsonObject;*/
        }
    }


    @GetMapping("/logout")
    public ResponseData logout(HttpServletRequest request,HttpServletResponse response, Device device) throws IOException{
        Cookie[] cookies = request.getCookies();
        for(int i=0;i<cookies.length;i++){
            Cookie cookie = cookies[i];
            if("token".equals(cookie.getName())){
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.flushBuffer();
                break;
            }
        }
        SecurityUtils.getSubject().logout();

        return ResponseData.success("注销成功");
    }

    @GetMapping("/token/refresh")
    public ResponseData refreshToken(HttpServletRequest request,HttpServletResponse response, Device device) throws IOException{
        // 先从Header里面获取
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            // 获取不到再从Parameter中拿
            token = request.getParameter("token");
            // 还是获取不到再从Cookie中拿
            if(StringUtils.isEmpty(token)){
                Cookie[] cookies = request.getCookies();
                if(cookies != null){
                    for (Cookie cookie : cookies) {
                        if("token".equals(cookie.getName())){
                            token = cookie.getValue();
                            break;
                        }
                    }
                }
            }
        }

        token = tokenUtil.refreshToken(token);
        return ResponseData.success(token);
    }

}
