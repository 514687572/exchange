package com.cmd.exchange.common.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lwx 2017/09/01
 */
public class OAuth2Token implements AuthenticationToken {

    private String token;

    private boolean isLogin;

    public OAuth2Token(String token) {
        this.token = token;
    }

    public OAuth2Token(String token, boolean isLogin) {
        this.token = token;
        this.isLogin = isLogin;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

}
