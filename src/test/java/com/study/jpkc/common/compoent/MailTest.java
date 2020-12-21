package com.study.jpkc.common.compoent;

import com.study.jpkc.common.component.MailComponent;
import com.study.jpkc.common.dto.RegisterMailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

/**
 * @Author Harlan
 * @Date 2020/12/21
 */
@SpringBootTest
public class MailTest {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    MailComponent mailComponent;

    @Test
    void sentMailTest() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("邮件发送测试");
        mailMessage.setText("这是一封邮件发送测试，请问回复。");
        mailMessage.setFrom("i102443@163.com");
        mailMessage.setTo("1353662613@qq.com");
        mailSender.send(mailMessage);
    }

    @Test
    void userRegisterMailSendTest() {
        RegisterMailDto dto = new RegisterMailDto(null , null);
        try {
            mailComponent.userRegisterMailSend(dto);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
