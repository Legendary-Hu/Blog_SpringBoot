package com.shnu.vueblog.dto;
/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/18 0:58
 */

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: vueblog
 *
 * @description: 封装前端传过来的参数
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-18 00:58
 **/
@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码名不能为空")
    private String password;
}
