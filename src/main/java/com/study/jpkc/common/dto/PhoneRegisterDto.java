package com.study.jpkc.common.dto;

import com.study.jpkc.utils.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户手机注册数据封装
 * @Author Harlan
 * @Date 2021/1/4
 */
@Data
public class PhoneRegisterDto implements Serializable {

    @NotBlank(message = "手机号不能位空")
    @Pattern(regexp = RegexUtils.PHONE_REGEX, message = RegexUtils.INCORRECT_FORMAT_PHONE)
    private String userPhone;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegexUtils.PASSWORD_REGEX, message = RegexUtils.INCORRECT_FORMAT_PASSWORD)
    private String password;
    @NotBlank(message = "手机验证码不能为空")
    @Pattern(regexp = RegexUtils.SMS_VERIFY_CODE_REGEX, message = RegexUtils.INCORRECT_FORMAT_SMS_VERIFY_CODE)
    private String smsVerifyCode;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = RegexUtils.VERIFY_CODE_REGEX, message = RegexUtils.INCORRECT_FORMAT_VERIFY_CODE)
    private String verifyCode;
}
