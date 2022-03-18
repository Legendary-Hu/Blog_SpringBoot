package com.shnu.vueblog.shiro;
/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/17 14:02
 */

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: vueblog
 *
 * @description: 生成Jwt Token
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-17 14:02
 **/
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
