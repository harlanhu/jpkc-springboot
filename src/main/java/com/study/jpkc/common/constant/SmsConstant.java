package com.study.jpkc.common.constant;

/**
 * @Author Harlan
 * @Date 2021/3/29
 */
public final class SmsConstant {

    private SmsConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INFO_EDITED = "【精品课程网】尊敬的客户，您的账户(${userName})信息已被修改，如非本人操作，请尽快修改密码";

    public static final String REGISTER = "【精品课程网】尊敬的客户，欢迎您的注册精品课程网。您当前的验证码是${verifyCode}，请与10分钟内正确输入。如非本人操作，请忽略此短信。";

    public static final String VERIFY_CODE = "【精品课程网】尊敬的客户，您当前的验证码是${verifyCode}，请与10分钟内正确输入。如非本人操作，请忽略此短信。";
}
