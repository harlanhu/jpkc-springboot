package com.study.jpkc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/29
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    void sendRegisterMailTest() {
        boolean b = userService.registerDefaultUser("13500000007", "1353662613@qq.com", "Hhn004460");
        assertThat(b).isTrue();
    }
}
