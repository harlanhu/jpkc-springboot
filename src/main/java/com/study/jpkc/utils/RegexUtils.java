package com.study.jpkc.utils;

/**
 * @Author Harlan
 * @Date 2020/12/22
 * @desc 正则判断工具类
 */
public class RegexUtils {

    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String PHONE_REGEX = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{3,15}$";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
    public static final String VERIFY_CODE_REGEX = "^[A-Za-z0-9]{4}$";
    public static final String SMS_VERIFY_CODE_REGEX = "^[0-9]{4}$";

    public static final String INCORRECT_FORMAT_VERIFY_CODE = "验证码格式不正确";
    public static final String INCORRECT_FORMAT_PHONE = "手机号码格式不正确";
    public static final String INCORRECT_FORMAT_EMAIL = "邮箱格式不正确";
    public static final String INCORRECT_FORMAT_PASSWORD = "密码格式不正确";
    public static final String INCORRECT_FORMAT_SMS_VERIFY_CODE = "手机验证码格式不正确";

    public static boolean emailMatches(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean phoneMatches(String phone) {
        return phone.matches(PHONE_REGEX);
    }

    public static boolean usernameMatches(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean passwordMatches(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean verifyCodeMatches(String verifyCode) {
        return verifyCode.matches(VERIFY_CODE_REGEX);
    }

    public static boolean smsVerifyCodeMatches(String smsVerifyCode) {
        return SMS_VERIFY_CODE_REGEX.matches(smsVerifyCode);
    }

    public static boolean isUsername(String username) {
        return emailMatches(username) || phoneMatches(username) || usernameMatches(username);
    }
}
