package com.study.jpkc.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Harlan
 * @Date 2020/12/31
 */
class TimeUtilsTest {

    Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    @Test
    void getTime() {
        logger.debug(TimeUtils.getTime(1,1,1,1).toString());
    }
}