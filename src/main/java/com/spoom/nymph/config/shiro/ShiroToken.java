package com.spoom.nymph.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * package com.spoom.nymph.config.shiro
 *
 * @author spoomlan
 * @date 2018/10/24
 */
public class ShiroToken implements AuthenticationToken {
    private String token;

    public ShiroToken(String token) {
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
