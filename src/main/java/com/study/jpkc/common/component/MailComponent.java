package com.study.jpkc.common.component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.study.jpkc.common.dto.RegisterMailDto;
import lombok.Setter;
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
@Setter
@Slf4j
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    private String domainName;

    private String mailFrom;

    private String region;

    private String accessKey;

    private String accessSecret;

    private IClientProfile profile;

    private IAcsClient client;

    public MailComponent() {
        this.profile = DefaultProfile.getProfile(region, accessKey, accessSecret);
        this.client = new DefaultAcsClient(profile);
    }

    /**
     * 注册邮件发送
     *
     * @param mailDto 邮件参数封装
     * @throws MessagingException 发送异常
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.register.mail"),
            exchange = @Exchange("amq.direct")
    ))
    public void userRegisterMailSend(RegisterMailDto mailDto) throws MessagingException {
        //创建邮件
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setSubject("欢迎注册精品课程网站");
        String text = "欢迎您注册！你的账号为" + mailDto.getUser().getUsername() + "<a href='" + domainName + "/user/activate/" + mailDto.getActivateUrl() + "'>点击立即激活账号</a>";
        messageHelper.setText(text, true);
        messageHelper.setTo(mailDto.getUser().getUserEmail());
        messageHelper.setFrom(mailFrom);
        mailSender.send(mailMessage);
        log.info("正在发送激活邮件至：" + mailDto.getUser().getUserEmail() + "，激活链接为：" + domainName + "/user/activate/" + mailDto.getActivateUrl());
    }

    public void getMailRequest() {
        SingleSendMailRequest request = new SingleSendMailRequest();
        SingleSendMailResponse response = null;
        try {
            request.setAccountName("");
            request.setFromAlias("Test");
            request.setAddressType(1);
            request.setTagName("");
            request.setReplyToAddress(true);
            request.setToAddress("");
            request.setSubject("");
            request.setHtmlBody("");
            request.setMethod(MethodType.POST);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        assert response != null;
        log.info(response.getRequestId());
    }
}
