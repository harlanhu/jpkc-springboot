package com.study.jpkc.common.component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.study.jpkc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author Harlan
 * @Date 2020/12/22
 * @desc 短信发送组件
 */
@Component
@ConfigurationProperties("aliyun.sms")
@Slf4j
public class SmsComponent {

    private String accessKeyId;
    private String accessSecret;
    private String regionId;
    private String signName;
    private String sysDomain;
    private String sysVersion;
    private final String VERIFY_CODE = "verifyCode";
    private final int EXPIRES_TIME = 60 * 10;
    private final int LIMIT_TIMES = 5;
    private final String TIMES = "times";

    public static final String FAIL_SEND_MESSAGE = "短信发送失败，请稍后再试";
    public static final String SUCCESS_SEND_MESSAGE = "短信发送成功";
    public static final String EXPIRES_CODE = "验证码已过期";
    public static final String OVER_LIMIT_TIMES = "发送次数过多，请稍后再试";

    @Autowired
    private RedisUtils redisUtils;

    public String sendVerifyCodeSms(String phone) {
        String key  = phone + VERIFY_CODE;
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        String verifyCode = String.valueOf(new Random().nextInt(9999));
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", "code");
        request.putQueryParameter("TemplateParam", verifyCode);
        try {
            CommonResponse response = client.getCommonResponse(request);
            //将验证码等信息存入redis
            redisUtils.set(key, verifyCode, EXPIRES_TIME);
            log.info("正在发送短信至：" + phone + " ====>> " + "验证码为：" + verifyCode);
            return response.getData();
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
