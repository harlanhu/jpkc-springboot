package com.study.jpkc.redis;

import com.study.jpkc.entity.Account;
import com.study.jpkc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 20:06
 * @desc redisUtils测试
 */
@SpringBootTest
@Slf4j
public class RedisUtilsTest {

    @Autowired
    RedisUtils redisUtils;

    @Test
    void saveTest() {
        redisUtils.set("test", "测试");
        Map<String, Object> map = new HashMap<>();
        map.put("1", "一");
        redisUtils.setHash("testHash", map);
        Account account = new Account("test", "123456", "123123", "密码");
        redisUtils.set("account", account);
    }

    @Test
    void getTest() {
        log.info((String) redisUtils.get("test"));
        System.out.println(redisUtils.getHash("testHash"));
    }

    @Test
    void delTest() {
        redisUtils.del("");
        redisUtils.del("test");
        redisUtils.del("testHash");
        redisUtils.del("account");
    }
}
