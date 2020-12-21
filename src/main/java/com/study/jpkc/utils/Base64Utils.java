package com.study.jpkc.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 21:41
 * @desc Base64工具类
 */
public class Base64Utils {

    /**
     * 加密
     * @param code 被加密信息
     * @return 加密信息
     */
    public static String encode(String code) {
        byte[] encode = Base64.getEncoder().encode(code.getBytes(StandardCharsets.UTF_8));
        return Arrays.toString(encode);
    }

    /**
     * 解密
     * @param code 被解密信息
     * @return 解密信息
     */
    public static String decode(String code) {
        byte[] decode = Base64.getDecoder().decode(code);
        return Arrays.toString(decode);
    }
}
