package com.study.jpkc.common.compoent;

import com.study.jpkc.common.component.MailComponent;
import com.study.jpkc.common.dto.MailDto;
import com.study.jpkc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @Author Harlan
 * @Date 2020/12/21
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailTest {

    @Autowired
    MailComponent mailComponent;

    @Test
    void sendTestMailTest() {
        String mailId = mailComponent.sendTestMail("1353662613@qq.com", "Test", "<h1>这是一个测试邮件</h1><a href='http://www.baidu.com'>链接测试</a>");
        System.out.println(mailId);
    }

    @Test
    void sendRegisterMailTest() {
        User u = new User();
        u.setUserEmail("1353662613@qq.com");
        u.setUserCreated(LocalDateTime.now());
        mailComponent.sendRegisterMail(new MailDto("http://www.baidu.com", null, u));
    }

    @Test
    void sendVerifyMailTest() {
        User u = new User();
        u.setUserEmail("1353662613@qq.com");
        MailDto mailDto = new MailDto(null, "HELLO", u);
        mailComponent.sendVerifyCode(mailDto);
    }
}
