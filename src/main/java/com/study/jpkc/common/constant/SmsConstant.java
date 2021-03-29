package com.study.jpkc.common.constant;

/**
 * @Author Harlan
 * @Date 2021/3/29
 */
public final class SmsConstant {

    private SmsConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INFO_EDITED = "您的账户(${userName})信息已被修改，如非本人操作，请尽快修改密码";
}
