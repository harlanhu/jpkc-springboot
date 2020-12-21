package com.study.jpkc.compoent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @Author Harlan
 * @Date 2020/12/21
 */
@SpringBootTest
public class MailTest {

    @Autowired
    JavaMailSender mailSender;

    @Test
    void sentMailTest() {
    }
}
