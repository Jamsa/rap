package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.core.security.jwt.JwtToken;
import com.github.jamsa.rap.core.util.TokenUtil;
import com.github.jamsa.rap.model.LoginUser;
import com.github.jamsa.rap.model.ResponseData;
import com.github.jamsa.rap.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity login(@RequestBody LoginUser loginUser, HttpServletResponse response, Device device) throws IOException{
        String username =loginUser.getUsername();
        String password = loginUser.getPassword();
        String passwd = userService.getUserPassword(username);

        if(!password.equals(passwd)) {
            ResponseData responseData = ResponseData.error("用户名或密码错误");
            return ResponseEntity.badRequest().body(responseData);
        }

        // 验证用户名密码成功后生成token
        String token = tokenUtil.generateToken(username, device);
        // 构建JwtToken
        JwtToken jwtToken = new JwtToken();
        jwtToken.setPrincipal(username);
        jwtToken.setToken(token);

        Cookie cookie =new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600 * 5);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.flushBuffer();

        Map result = new HashMap();
        result.put("username",username);
        result.put("token",token);

        return ResponseEntity.ok(result);



        /*
        Subject subject = SecurityUtils.getSubject();
        ResponseData responseData = ResponseData.error("用户名或密码错误");
        try {
            // 该方法会调用JwtRealm中的doGetAuthenticationInfo方法
            subject.login(jwtToken);
        } catch (UnknownAccountException exception) {
            logger.error("账号不存在",exception);
            responseData.setMessage("帐号不存在");
        } catch (IncorrectCredentialsException exception) {
            logger.error("错误的凭证，用户名或密码不正确",exception);
            responseData.setMessage("错误的凭证，用户名或密码不正确");
        } catch (LockedAccountException exception) {
            logger.error("账户已锁定",exception);
            responseData.setMessage("账户已锁定");
        } catch (ExcessiveAttemptsException exception) {
            logger.error("错误次数过多",exception);
            responseData.setMessage("错误次数过多");
        } catch (AuthenticationException exception) {
            logger.error("认证失败",exception);
            responseData.setMessage("认证失败");
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

            //return ResponseData.success(token);
            Map result = new HashMap();
            result.put("username",username);
            result.put("token",token);
            Set<String> permissions = new HashSet();
            Set<String> roles = new HashSet();
            result.put("permissions",permissions);
            result.put("roles",roles);


            RealmSecurityManager realmSecurityManager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
            realmSecurityManager.getRealms().stream().filter(realm -> realm instanceof JwtRealm).map(realm -> {
                SimpleAuthorizationInfo authorizationInfo = ((SimpleAuthorizationInfo)realm.getAuthenticationInfo(jwtToken));
                permissions.addAll(authorizationInfo.getStringPermissions());
                roles.addAll(authorizationInfo.getRoles());
            });

            return ResponseEntity.ok(result);

        }else{
            return ResponseEntity.badRequest().body(responseData);
        }
        */
    }


    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request,HttpServletResponse response, Device device) throws IOException{

        SecurityUtils.getSubject().logout();
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

        //return ResponseData.success("注销成功");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public ResponseEntity refreshToken(HttpServletRequest request,HttpServletResponse response, Device device) {
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
        //return ResponseData.success(token);
        return ResponseEntity.ok(token);
    }

}
