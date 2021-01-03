package com.study.jpkc.common.dto;

import com.study.jpkc.utils.RegexUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 16:06
 * @desc LoginDto
 */
@Data
public class LoginDto implements Serializable {

    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String PHONE_REGEX = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{3,15}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";


    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = LoginDto.PASSWORD_REGEX, message = "密码格式未满足要求")
    private String password;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = RegexUtils.VERIFY_CODE_REGEX, message = "验证码格式不正确")
    private String verifyCode;

}
