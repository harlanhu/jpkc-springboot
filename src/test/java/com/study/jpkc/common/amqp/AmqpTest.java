package com.study.jpkc.common.amqp;

import com.study.jpkc.shiro.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 22:21
 * @desc 消息中间件测试
 */
@SpringBootTest
@Slf4j
class AmqpTest {

    @Autowired
    private RabbitMessagingTemplate template;

    @Test
    void sendMessageTest() {
        AccountProfile profile = new AccountProfile("123", "admin", "111", "123@test.com", "test.jpg");
        template.convertAndSend("amq.direct", "user.register.mail", profile);
    }

    @Test
    void receiveMessageTest() {
        AccountProfile profile = template.receiveAndConvert("user.register.mail", AccountProfile.class);
        if (profile == null) throw new AssertionError();
        log.info(profile.getUsername());
    }
}
