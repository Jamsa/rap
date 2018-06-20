package com.github.jamsa.rap.core.security.jwt;

import com.github.jamsa.rap.core.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhujie on 2018/1/9.
 */
public class JwtRealm extends AuthorizingRealm {

    private TokenUtil tokenUtil;

    public JwtRealm(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
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

        //TODO: 查询角色
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("superadmin");

        authorizationInfo.setRoles(roles);

        //TODO: 查询权限
        Set<String> permissions = new HashSet<>();
        permissions.add("system:*");

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

        return new SimpleAuthenticationInfo(username,tokenStr,getName());
    }
}
