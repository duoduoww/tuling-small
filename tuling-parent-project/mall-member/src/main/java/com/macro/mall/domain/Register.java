package com.macro.mall.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author kzc
 */

@Data
public class Register {

    @NotBlank(message = "联系号码不能为空")
    @Pattern(regexp = "^1[3|4|5|7|8|9][0-9]\\d{8}$",message = "电话号码格式不正确")
    private String phone;

    @NotBlank(message = "动态校验码不能为空")
    @Length(max = 6,min = 6,message = "动态校验码只能是6位")
    private String otpCode;

    @NotBlank(message = "用户名不能为空")
    @Length(max = 10,min = 4,message = "用户名长度必须在4-10字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 20,min = 4,message = "密码长度必须在4-20字符之间")
    private String password;


}
