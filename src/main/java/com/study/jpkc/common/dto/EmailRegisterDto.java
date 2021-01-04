package com.study.jpkc.common.dto;

import com.study.jpkc.utils.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 邮件注册参数封装
 * @Author Harlan
 * @Date 2021/1/4
 */
@Data
public class EmailRegisterDto implements Serializable {

    @NotBlank(message = "邮箱不能未空")
    @Pattern(regexp = RegexUtils.EMAIL_REGEX, message = RegexUtils.INCORRECT_FORMAT_EMAIL)
    private String userEmail;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = RegexUtils.INCORRECT_FORMAT_PASSWORD)
    private String password;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = RegexUtils.VERIFY_CODE_REGEX, message = RegexUtils.INCORRECT_FORMAT_VERIFY_CODE)
    private String verifyCode;
}
