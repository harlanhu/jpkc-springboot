package com.study.jpkc.common.amqp;

import com.study.jpkc.common.dto.MailDto;
import com.study.jpkc.entity.User;
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
    void sendRegisterMessageTest() {
        User user = new User();
        user.setUserEmail("1353662613@qq.com");
        user.setUserCreated(LocalDateTime.now());
        MailDto mailDto = new MailDto("http://www.baidu.com",null, user);
        template.convertAndSend("amq.direct", "user.register.mail", mailDto);
    }

    @Test
    void sendMessageTest() {
        template.convertAndSend("amq.direct", "user.register.mail", "test");
    }

    @Test
    void receiveMessageTest() {
        MailDto mailDto = template.receiveAndConvert("user.register.mail", MailDto.class);
        if (mailDto == null) throw new AssertionError();
        log.info(mailDto.getActivateUrl());
    }
}
