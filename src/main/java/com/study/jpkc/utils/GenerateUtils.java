package com.study.jpkc.utils;

import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2021/3/5
 */
public class GenerateUtils {

    /**
     * 生成UUID
     * @return uuid
     */
    public static String getUUID () {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
