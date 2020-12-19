package com.study.jpkc.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 21:22
 * @desc jwtToken
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
