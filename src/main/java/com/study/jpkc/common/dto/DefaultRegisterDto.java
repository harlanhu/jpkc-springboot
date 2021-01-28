package com.study.jpkc.common.dto;

import com.study.jpkc.utils.RegexUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author Harlan
 * @Date 2021/1/28
 * @Desc 默认注册Dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DefaultRegisterDto {

    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = "邮箱格式错误")
    private String userEmail;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = "密码格式不正确")
    private String password;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegexUtils.PHONE_REGEX, message = "手机号格式不正确")
    private String userPhone;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = RegexUtils.SMS_VERIFY_CODE_REGEX, message = "验证码格式不正确")
    private String smsVerifyCode;
}
