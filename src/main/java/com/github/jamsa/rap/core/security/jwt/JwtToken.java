package com.github.jamsa.rap.core.security.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by zhujie on 2018/1/9.
 */
public class JwtToken implements AuthenticationToken {
    private String principal;

    private String token;

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
