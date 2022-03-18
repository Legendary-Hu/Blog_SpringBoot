package com.shnu.vueblog.controller;
/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/18 0:51
 */

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.shnu.vueblog.dto.LoginDto;
import com.shnu.vueblog.dto.RegistryDto;
import com.shnu.vueblog.entity.User;
import com.shnu.vueblog.service.UserService;
import com.shnu.vueblog.util.JwtUtils;
import com.shnu.vueblog.util.Result;
import org.apache.catalina.security.SecurityUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @program: vueblog
 *
 * @description: 登录接口
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-18 00:51
 **/
@RestController
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username",loginDto.getUsername()));
        Assert.notNull(user,"用户不存在"); //hutool断点异常处理,user为空时会抛出IllegalArgumentException
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确！");
        }
        String jwt = jwtUtils.generateToken(user.getId()); //生成jwt
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expost-Headers","Authorization");

        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );
    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }


    @PostMapping("/registry")
    public Result registry(@Validated @RequestBody RegistryDto registryDto){
        //查询数据库中是否有相同的用户名的用户
        User user1 = userService.getOne(new QueryWrapper<User>().eq("username", registryDto.getUsername()));
        //查询邮箱是否已被注册
        User user2 = userService.getOne(new QueryWrapper<User>().eq("email", registryDto.getEmail()));
        Assert.isTrue(user1==null,"该用户名已存在！");
        Assert.isTrue(user2==null,"该邮箱已被注册！");

        User user = new User();
        user.setCreated(LocalDateTime.now());
        user.setStatus(0);
        user.setPassword(SecureUtil.md5(registryDto.getPassword()));
        BeanUtils.copyProperties(registryDto,user,"password");
        userService.save(user);
        return Result.success(null);
    }
}
