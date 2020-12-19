package com.study.jpkc.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 17:47
 * @desc
 */
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    void saveTest() {
        redisTemplate.opsForValue().set("test", "测试redis缓存");
    }

    @Test
    void getTest() {
        String test = (String) redisTemplate.opsForValue().get("test");
        log.info(test);
    }

    @Test
    void deleteTest() {
        redisTemplate.delete("test");
    }
}
