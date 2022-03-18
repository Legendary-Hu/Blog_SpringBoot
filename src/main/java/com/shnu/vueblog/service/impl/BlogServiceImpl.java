package com.shnu.vueblog.service.impl;

import com.shnu.vueblog.entity.Blog;
import com.shnu.vueblog.mapper.BlogMapper;
import com.shnu.vueblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huxiang
 * @since 2022-03-14
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
