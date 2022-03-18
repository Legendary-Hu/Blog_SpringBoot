package com.shnu.vueblog.shiro;

import com.shnu.vueblog.entity.User;
import com.shnu.vueblog.service.UserService;
import com.shnu.vueblog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: vueblog
 *
 * @description: None
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-16 14:55
 **/

//自定义AccountRealm类
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    //判断toekn是否是支持的JwtToken
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String userid = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(userid);
        if (user==null){
            throw new UnknownAccountException("用户不存在！");
        }
        if (user.getStatus()==-1){
            throw new LockedAccountException("该用户已被锁定！");
        }
        AccountProfile accountProfile = new AccountProfile();
        BeanUtils.copyProperties(user,accountProfile);  //复制属性到accountProfile
        //在验证用户可用后返回一些基本信息给Shiro
        return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(),getName());
    }
}
