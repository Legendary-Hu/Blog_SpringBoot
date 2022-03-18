package com.shnu.vueblog.shiro;/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/17 16:17
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @program: vueblog
 *
 * @description: 用户信息简介
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-17 16:17
 **/
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;
}
