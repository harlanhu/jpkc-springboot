package com.study.jpkc.common.component;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.study.jpkc.common.dto.RegisterMailDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 20:00
 * @desc 邮件组件
 */
@Component
@Setter
@Slf4j
public class MailComponent {

    private IAcsClient iAcsClient;

    public MailComponent(IAcsClient iAcsClient) {
        this.iAcsClient = iAcsClient;
    }

    private static final String NO_REPLY_ACCOUNT = "noreply@1024.gold";

    private static final String NO_REPLY_ALIAS = "精品课程网";

    private static final String TEXT_TAG = "Test";

    private static final String REGISTER_TAG = "Register";

    private static final String REGISTER_SUBJECT = "精品课程网账号激活";

    /**
     * 注册邮件发送
     * @param mailDto 注册信息
     * @return 邮件id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.register.mail"),
            exchange = @Exchange("amq.direct")
    ))
    public String sendRegisterMail(RegisterMailDto mailDto) {
        String bodyText = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>激活邮件</title>\n" +
                "        <style type=\"text/css\">\n" +
                "            * {\n" +
                "                margin: 0;\n" +
                "                padding: 0;\n" +
                "                box-sizing: border-box;\n" +
                "                font-family: Arial, Helvetica, sans-serif;\n" +
                "               }\n" +
                "            body {\n" +
                "                background-color: #ECECEC;\n" +
                "                 }\n" +
                "            .container {\n" +
                "                width: 800px;\n" +
                "                margin: 50px auto;\n" +
                "             }\n" +
                "            .header {\n" +
                "                height: 80px;\n" +
                "                background-color: #49bcff;\n" +
                "                border-top-left-radius: 5px;\n" +
                "                border-top-right-radius: 5px;\n" +
                "                padding-left: 30px;\n" +
                "             }\n" +
                "            .header h2 {\n" +
                "                padding-top: 25px;\n" +
                "                color: white;\n" +
                "                       }\n" +
                "            .content {\n" +
                "                background-color: #fff;\n" +
                "                padding-left: 30px;\n" +
                "                padding-bottom: 30px;\n" +
                "                border-bottom: 1px solid #ccc;\n" +
                "                }\n" +
                "            .content h2 {\n" +
                "                padding-top: 20px;\n" +
                "                padding-bottom: 20px;\n" +
                "                }\n" +
                "            .content p {\n" +
                "                padding-top: 10px;\n" +
                "                       }\n" +
                "            .footer {\n" +
                "                background-color: #fff;\n" +
                "                border-bottom-left-radius: 5px;\n" +
                "                border-bottom-right-radius: 5px;\n" +
                "                padding: 35px;\n" +
                "            }\n" +
                "            .footer p {\n" +
                "                color: #747474;\n" +
                "                padding-top: 10px;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"header\">\n" +
                "                <h2>欢迎加入精品课程网</h2>\n" +
                "        </div>\n" +
                "            <div class=\"content\">\n" +
                "                <h2>亲爱的用户您好</h2>\n" +
                "                <p>您的邮箱：<b>" +
                "                   <span>" + mailDto.getUser().getUserEmail() + "</span>" +
                "                   </b>" +
                "               </p>\n" +
                "                <p>您的激活链接：<b><span>" + mailDto.getActivateUrl() + "</span></b></p>\n" +
                "                <p>您注册时的日期：<b><span>" + mailDto.getUser().getUserCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "</span></b></p>\n" +
                "                <p>当您在使用本网站时，务必要遵守法律法规</p>\n" +
                "                <p>如果您有什么疑问可以联系管理员，Email: <b>isharlan.hu@gmail.com</b></p>\n" +
                "            </div>\n" +
                "            <div class=\"footer\">\n" +
                "                <p>此为系统邮件，请勿回复</p>\n" +
                "                <p>请保管好您的信息，避免被他人盗用</p>\n" +
                "                <p>©Jpkc</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";
        SingleSendMailRequest mailRequest = getMailRequest(NO_REPLY_ACCOUNT, NO_REPLY_ALIAS, REGISTER_TAG, mailDto.getUser().getUserEmail(), REGISTER_SUBJECT, bodyText);
        SingleSendMailResponse acsResponse = null;
        try {
            acsResponse = iAcsClient.getAcsResponse(mailRequest);
        } catch (ClientException e) {
            log.error("发送注册邮件失败: " + e.getErrMsg());
        }
        assert acsResponse != null;
        return acsResponse.getEnvId();
    }

    /**
     * 测试邮件
     * @param toAddress 接收地址
     * @param subject 主题
     * @param bodyText 内容
     * @return 邮件id
     */
    public String sendTestMail(String toAddress, String subject, String bodyText) {
        SingleSendMailRequest mailRequest = getMailRequest(NO_REPLY_ACCOUNT, NO_REPLY_ALIAS, TEXT_TAG, toAddress, subject, bodyText);
        SingleSendMailResponse acsResponse = null;
        try {
            acsResponse = iAcsClient.getAcsResponse(mailRequest);
        } catch (ClientException e) {
            log.error(e.getErrMsg());
        }
        assert acsResponse != null;
        return acsResponse.getEnvId();
    }

    /**
     * 通用邮件
     * @param accountName 发送账户
     * @param fromAlias 别名
     * @param tagName 标签
     * @param toAddress 接收地址
     * @param subject 主题
     * @param bodyText 内容
     * @return 邮件id
     */
    public SingleSendMailRequest getMailRequest(String accountName, String fromAlias, String tagName, String toAddress, String subject, String bodyText) {
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setAccountName(accountName);
        request.setFromAlias(fromAlias);
        request.setAddressType(1);
        request.setTagName(tagName);
        request.setReplyToAddress(true);
        request.setToAddress(toAddress);
        request.setSubject(subject);
        request.setHtmlBody(bodyText);
        request.setSysMethod(MethodType.POST);
        return request;
    }
}
