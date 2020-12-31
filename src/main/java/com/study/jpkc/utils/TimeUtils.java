package com.study.jpkc.utils;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Author Harlan
 * @Date 2020/12/31
 * @Desc 时间工具类
 */
public class TimeUtils {

    static final Long SECOND = 1000L;
    static final Long MINUTE = SECOND * 60;
    static final Long HOUR = MINUTE * 60;
    static final Long DAY = HOUR * 24;

    public static Long getTime(Integer day, Integer hour, Integer minute, Integer second) {
        if (ObjectUtil.isAllNotEmpty(day, hour, minute, second)) {
            return day * DAY + hour * HOUR + minute * MINUTE + second * SECOND;
        }
        return 0L;
    }
}
