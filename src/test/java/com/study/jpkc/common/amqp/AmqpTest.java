package com.study.jpkc.common.amqp;

import com.study.jpkc.common.dto.RegisterMailDto;
import com.study.jpkc.entity.User;
import com.study.jpkc.shiro.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        User user = new User();
        user.setUserEmail("1353662613@qq.com");
        user.setUserCreated(LocalDateTime.now());
        RegisterMailDto mailDto = new RegisterMailDto("http://www.baidu.com", user);
        template.convertAndSend("amq.direct", "user.register.mail", mailDto);
    }

    @Test
    void receiveMessageTest() {
        AccountProfile profile = template.receiveAndConvert("user.register.mail", AccountProfile.class);
        if (profile == null) throw new AssertionError();
        log.info(profile.getUsername());
    }
}
