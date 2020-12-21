package com.study.jpkc.common.component;

import com.study.jpkc.common.dto.RegisterMailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 20:00
 * @desc 邮件组件
 */
@Component
@ConfigurationProperties(prefix = "spring.mail")
@Slf4j
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    private static final String DOMAIN_NAME = "localhost";

    private static final String MAIL_FROM = "i102443@163.com";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.register.mail"),
            exchange = @Exchange("amq.direct")
    ))
    public void userRegisterMailSend(RegisterMailDto mailDto) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
        messageHelper.setSubject("欢迎注册精品课程网站");
        String text = "欢迎您注册！你的账号为"+ mailDto.getUser().getUsername() +"<a href='" + DOMAIN_NAME + "/user/activate/" + mailDto.getActivateUrl() + "'>点击立即激活账号</a>";
        messageHelper.setText(text, true);
        messageHelper.setTo(mailDto.getUser().getUserEmail());
        messageHelper.setFrom(MAIL_FROM);
        mailSender.send(mailMessage);
        log.info("正在发送邮件至：" + mailDto.getUser().getUserEmail());
    }
}
