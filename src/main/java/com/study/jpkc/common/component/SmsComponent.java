package com.study.jpkc.common.component;

import com.study.jpkc.common.exception.CommonException;
import com.study.jpkc.utils.RedisUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author Harlan
 * @Date 2020/12/22
 * @desc 短信发送组件
 */
@Component
@ConfigurationProperties("aliyun.sms")
@Setter
@Slf4j
public class SmsComponent {

    private String accessKeyId;
    private String accessSecret;
    private String regionId;
    private String signName;
    private String sysDomain;
    private String sysVersion;
    private final String VERIFY_CODE = "SmsController";
    private final int EXPIRES_TIME = 60 * 10;
    private final int LIMIT_TIMES = 5;
    private final String TIMES = "times";
    private final String DATE = "date";
    private final int FREEZE_TIME = 60 * 30;
    private final String IS_FREEZE = "isFreeze";

    public static final String FAIL_SEND_MESSAGE = "短信发送失败，请稍后再试";
    public static final String SUCCESS_SEND_MESSAGE = "短信发送成功";
    public static final String EXPIRES_CODE = "验证码已过期";
    public static final String OVER_LIMIT_TIMES = "发送次数过多，请稍后再试";

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 发送验证码
     * @param phone 手机号
     * @return 是否成功
     */
    public String sendSmsVerifyCode(String phone) {
        String key = phone + VERIFY_CODE;
        //限制发送次数判断
        if (redisUtils.hasKey(key)) {
            Map<Object, Object> dataMap = redisUtils.getHash(key);
            //判读是否冻结
            boolean isFreeze = (boolean) dataMap.get(IS_FREEZE);
            if (isFreeze) {
                log.info("冻结： 发送次数过多，请" + redisUtils.getExpire(key) / 60 + "分钟后再试");
                return "发送次数过多，请" + redisUtils.getExpire(key) / 60 + "分钟后再试";
            }
            //判断是否在限制时间内
            LocalDateTime dateTime = (LocalDateTime) dataMap.get(DATE);
            LocalDateTime dateNow = LocalDateTime.now();
            if (dateTime.isAfter(dateNow)) {
                log.info("发送频率过快，请" +
                        ((dateTime.getSecond() + (dateTime.getMinute() * 60) + (dateTime.getHour() * 3600)) -
                                (dateNow.getSecond() + (dateNow.getMinute() * 60) + (dateNow.getHour() * 3600))) +
                        "秒后再试");
                return "发送频率过快，请" +
                        (dateTime.getSecond() + (dateTime.getMinute() * 60) + (dateTime.getHour() * 360) -
                                dateNow.getSecond() - (dateNow.getMinute() * 60) - (dateNow.getHour() * 360)) +
                        "秒后再试";
            }
            //判断是否超出发送次数
            int countNum = (int) dataMap.get(TIMES);
            if (countNum > LIMIT_TIMES) {
                //冻结30分钟
                redisUtils.setHashItem(key, IS_FREEZE, true);
                redisUtils.expire(key, FREEZE_TIME);
                log.info("发送次数过多，请" + redisUtils.getExpire(key) / 60 + "秒后再试");
                return "发送次数过多，请" + redisUtils.getExpire(key) / 60 + "秒后再试";
            }
            //发送短信 并跟新时间和发送次数
            String verifyCode = String.valueOf(((Math.random() * 9 + 1) * 100000));
            redisUtils.setHashItem(key, VERIFY_CODE, verifyCode);
            redisUtils.setHashItem(key, TIMES, countNum + 1);
            LocalDateTime futureTime = LocalDateTime.now().plusMinutes(1);
            redisUtils.setHashItem(key, DATE, futureTime);
            log.info("第" + (countNum + 1) + "次发送");
            return sendCode(phone, verifyCode);
        }
        //将验证码等信息存入redis
        Map<String, Object> dataMap = new HashMap<>(3);
        //当前时间延期1分钟
        LocalDateTime futureTime = LocalDateTime.now().plusMinutes(1);
        dataMap.put(DATE, futureTime);
        //初始化发送次数
        int countNum = 1;
        dataMap.put(TIMES, countNum);
        //存入验证码
        String verifyCode = String.valueOf(new Random().nextInt(9999));
        dataMap.put(VERIFY_CODE, verifyCode);
        //是否冻结
        dataMap.put(IS_FREEZE, false);
        //存入缓存并指定过期时间
        redisUtils.setHash(key, dataMap, EXPIRES_TIME);
        log.info("初次发送");
        return sendCode(phone, verifyCode);
    }

    /**
     * 验证并删除验证码码
     * @param phone 手机号
     * @param code 验证码
     * @return 是否成功
     */
    public boolean validateSmsVerifyCode(String phone, String code) {
        String key = phone + VERIFY_CODE;
        if (!redisUtils.hasKey(key)) {
            throw new CommonException(400, "手机验证码已过期，请重新获取");
        }
        String vCode = (String) redisUtils.getHash(key).get(VERIFY_CODE);
        if (code.equals(vCode)) {
            //删除验证码
            redisUtils.del(key);
            return true;
        }else {
            throw new CommonException(400, "手机验证码错误，请重新输入");
        }
    }

    /**
     * 发送验证码
     * @param phone 手机号
     * @param verifyCode 验证码
     * @return 成功信息
     */
    private String sendCode(String phone, String verifyCode) {
        log.info("正在发送短信至：" + phone + " ====>> " + "验证码为：" + verifyCode);
        return SUCCESS_SEND_MESSAGE;
//        //榛子短信
//        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "107601", "0b2d00f0-5253-43f6-a9e0-6c215885b291");
//        Map<String, Object> params = new HashMap<>(3);
//        params.put("number", phone);
//        params.put("templateId", "2915");
//        String[] templateParams = new String[2];
//        templateParams[0] = verifyCode;
//        templateParams[1] = "10分钟";
//        params.put("templateParams", templateParams);
//        try{
//            log.info("正在发送短信至：" + phone + " ====>> " + "验证码为：" + verifyCode);
//            return client.send(params);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return FAIL_SEND_MESSAGE;
//        }
//        //阿里云
//        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", regionId);
//        request.putQueryParameter("PhoneNumbers", phone);
//        request.putQueryParameter("SignName", signName);
//        request.putQueryParameter("TemplateCode", "code");
//        request.putQueryParameter("TemplateParam", verifyCode);
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            log.info("正在发送短信至：" + phone + " ====>> " + "验证码为：" + verifyCode);
//            return response.getData();
//        } catch (ClientException e) {
//            e.printStackTrace();
//            return FAIL_SEND_MESSAGE;
//        }
    }
}
