package com.study.jpkc.common.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Author Harlan
 * @Date 2020/12/21
 */
@Component
class MailComponent {

    @Autowired
    JavaMailSender mailSender;
}
