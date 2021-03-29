package com.study.jpkc.common.component;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.study.jpkc.common.constant.MailConstant;
import com.study.jpkc.common.dto.MailDto;
import com.study.jpkc.utils.MailUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 20:00
 * @desc 邮件组件
 */
@Component
@Setter
@Slf4j
public class MailComponent {

    private IAcsClient mailClient;

    public MailComponent(IAcsClient mailClient) {
        this.mailClient = mailClient;
    }

    private static final String NO_REPLY_ACCOUNT = "noreply@1024.gold";

    private static final String NO_REPLY_ALIAS = "精品课程网";

    private static final String TEXT_TAG = "Test";

    private static final String REGISTER_TAG = "Register";

    private static final String REGISTER_SUBJECT = "精品课程网账号激活";

    private static final String MAIL_VERIFY_CODE = "精品课程网验证码";

    /**
     * 注册邮件发送
     * @param mailDto 注册信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.register.mail"),
            exchange = @Exchange("amq.direct"),
            key = "user.register.mail"
    ))
    public void sendRegisterMail(MailDto mailDto) {
        String bodyText = MailUtils.getRegisterMailBody(mailDto.getUser().getUserEmail(), mailDto.getActivateUrl(), mailDto.getUser().getUserCreated());
        SingleSendMailRequest mailRequest = getMailRequest(NO_REPLY_ACCOUNT, NO_REPLY_ALIAS, REGISTER_TAG, mailDto.getUser().getUserEmail(), REGISTER_SUBJECT, bodyText);
        try {
            mailClient.getAcsResponse(mailRequest);
        } catch (ClientException e) {
            log.error("发送注册邮件失败: " + e.getErrMsg());
        }
    }

    /**
     * 邮件验证码发送
     * @param mailDto 邮箱信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("user.verify.mail"),
            exchange = @Exchange("amq.direct"),
            key = "user.verify.mail"
    ))
    public void sendVerifyCode(MailDto mailDto) {
        String bodyText = MailConstant.MAIL_VERIFY_TEMPLATE.replace("${verifyCode}", mailDto.getVerifyCode());
        SingleSendMailRequest mailRequest = getMailRequest(NO_REPLY_ACCOUNT, NO_REPLY_ALIAS, REGISTER_TAG, mailDto.getUser().getUserEmail(), MAIL_VERIFY_CODE, bodyText);
        try {
            mailClient.getAcsResponse(mailRequest);
        } catch (ClientException e) {
            log.error("发送验证码邮件失败: " + e.getErrMsg());
        }
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
            acsResponse = mailClient.getAcsResponse(mailRequest);
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
