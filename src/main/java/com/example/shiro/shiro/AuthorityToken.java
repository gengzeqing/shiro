package com.example.shiro.shiro;

import com.example.shiro.model.dto.LoginAdminResDTO;
import lombok.ToString;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @Author: yaokui
 * @Date: 2019/6/3
 */
@ToString
public class AuthorityToken implements AuthenticationToken {

    private static final long serialVersionUID = -7773801705155772802L;

    private String token;

    private LoginAdminResDTO loginAdminResDTO;

    public AuthorityToken(String token, LoginAdminResDTO loginAdminResDTO){
        this.token = token;
        this.loginAdminResDTO = loginAdminResDTO;
    }

    @Override
    public Object getPrincipal() {
        return loginAdminResDTO;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
