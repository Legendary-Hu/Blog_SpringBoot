package com.shnu.vueblog.dto;/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/18 17:44
 */

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: vueblog
 *
 * @description:
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-18 17:44
 **/
@Data
public class RegistryDto implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
}
