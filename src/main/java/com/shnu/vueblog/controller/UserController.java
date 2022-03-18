package com.shnu.vueblog.controller;


import com.shnu.vueblog.entity.User;
import com.shnu.vueblog.service.UserService;
import com.shnu.vueblog.util.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huxiang
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequiresAuthentication
    @GetMapping(value = "/index/{id}")
    public Result getInfomation(@PathVariable("id") Integer id){
       User user = userService.getById(id);
       return Result.success(user);
    }

    @PostMapping(value = "/save")
    public Result save(@Validated @RequestBody User user){
        return Result.success(user);
    }

}
