package com.shnu.vueblog.controller;



import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shnu.vueblog.entity.Blog;
import com.shnu.vueblog.service.BlogService;
import com.shnu.vueblog.util.Result;
import com.shnu.vueblog.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huxiang
 * @since 2022-03-14
 */
@RestController

public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs") //分页查询
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pagedata = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pagedata);
    }
    @GetMapping("/blog/{id}") //详情
    public Result detail(@PathVariable("id") Long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");

        return Result.success(blog);
    }
    @RequiresAuthentication
    @PostMapping("/blog/edit")  //编辑和添加
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        //Id存在则为编辑操作
        if(blog.getId()!=null){
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().longValue()== ShiroUtil.getProfile().getId().longValue(),"你没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);

        }
        BeanUtils.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }
}
