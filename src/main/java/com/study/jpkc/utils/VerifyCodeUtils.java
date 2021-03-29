package com.study.jpkc.utils;

import java.util.Random;

/**
 * @Author Harlan
 * @Date 2021/3/29
 */
public class VerifyCodeUtils {

    public static String getVerifyCode(int charCount) {
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            char c = (char) (getVerifyCode(0, 10) + '0');
            charValue.append(String.valueOf(c));
        }
        return charValue.toString();
    }

    public static int getVerifyCode(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
