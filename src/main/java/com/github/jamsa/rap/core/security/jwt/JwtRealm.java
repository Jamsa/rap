package com.github.jamsa.rap.core.security.jwt;

import com.github.jamsa.rap.core.util.TokenUtil;
import com.github.jamsa.rap.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Created by zhujie on 2018/1/9.
 */
public class JwtRealm extends AuthorizingRealm {

    private TokenUtil tokenUtil;
    private UserService userService;

    public JwtRealm(TokenUtil tokenUtil, UserService userService) {
        this.tokenUtil = tokenUtil;
        this.userService = userService;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //return super.supports(token);
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = userService.getUserRoleCodes(username);

        authorizationInfo.setRoles(roles);

        Set<String> permissions = userService.getUserResourceCodes(username);

        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)token;

        String tokenStr = jwtToken.getToken();
        if(StringUtils.isEmpty(tokenStr)){
            throw new AuthenticationException("未认证用户，访问被拒绝！");
        }

        String username = tokenUtil.getUsernameFromToken(tokenStr);
        if(StringUtils.isEmpty(username)){
            throw new AuthenticationException("令牌失效，访问被拒绝！");
        }

        return new SimpleAuthenticationInfo(username,tokenStr,getName());
    }
}
