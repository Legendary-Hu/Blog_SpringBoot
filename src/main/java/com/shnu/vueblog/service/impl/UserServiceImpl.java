package com.shnu.vueblog.service.impl;

import com.shnu.vueblog.entity.User;
import com.shnu.vueblog.mapper.UserMapper;
import com.shnu.vueblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
