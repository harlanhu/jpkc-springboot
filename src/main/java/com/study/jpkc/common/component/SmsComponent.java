package com.study.jpkc.common.component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.study.jpkc.common.constant.SmsConstant;
import com.study.jpkc.common.exception.CommonException;
import com.study.jpkc.entity.User;
import com.study.jpkc.utils.RedisUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Harlan
 * @Date 2020/12/22
 * @desc 短信发送组件
 */
@Component
@Setter
@Slf4j
public class SmsComponent {

    private IAcsClient smsClient;

    private RedisUtils redisUtils;

    public SmsComponent(IAcsClient smsClient, RedisUtils redisUtils) {
        this.smsClient = smsClient;
        this.redisUtils = redisUtils;
    }

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

    private static final String USERNAME = "HarlanHu";
    private static final String PASSWORD = "Hhn004460";
    private static final String HTTP_URL = "http://api.smsbao.com/sms";

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
                log.info("发送频率过快");
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
            String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            redisUtils.setHashItem(key, VERIFY_CODE, verifyCode);
            redisUtils.setHashItem(key, TIMES, countNum + 1);
            LocalDateTime futureTime = LocalDateTime.now().plusMinutes(1);
            redisUtils.setHashItem(key, DATE, futureTime);
            log.info("第" + (countNum + 1) + "次发送");
            //sendMessage(phone, verifyCode);
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
        String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        dataMap.put(VERIFY_CODE, verifyCode);
        //是否冻结
        dataMap.put(IS_FREEZE, false);
        //存入缓存并指定过期时间
        redisUtils.setHash(key, dataMap, EXPIRES_TIME);
        log.info("初次发送");
        //sendMessage(phone, verifyCode);
        return sendCode(phone, verifyCode);
    }

    public boolean sendMessage(String phone, String message) {
        log.info("正在发送短信至：" + phone + " ====>> " + "内容为：" + message);
        return true;
    }

    public boolean sendInfoEditedMessage(User user) {
        String message = SmsConstant.INFO_EDITED.replace("${userName}", user.getUsername());
        dXinBaoSendMessage(user.getUserPhone(), message);
        return true;
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
//        try {
//            CommonResponse response = smsClient.getCommonResponse(
//                    getSmsRequest(phone, "verifyCode", verifyCode));
//            return response.getData();
//        } catch (ClientException e) {
//            log.error("短信发送失败: " + e.getErrMsg());
//            return FAIL_SEND_MESSAGE;
//        }
        //dXinBaoSendMessage(phone, SmsConstant.VERIFY_CODE.replace("${verifyCode}", verifyCode));
        log.info("正在发送短信至：" + phone + " ====>> " + "验证码为：" + verifyCode);
        return SUCCESS_SEND_MESSAGE;
    }

    public CommonRequest getDefaultRequest(String sysDomain, String sysVersion, String sysAction, String regionId, String signName) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
        request.setSysAction(sysAction);
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("SignName", signName);
        return request;
    }

    public CommonRequest getSmsRequest(String phone, String templateCode, String templateParam) {
        CommonRequest request = getDefaultRequest("dysmsapi.aliyuncs.com", "2017-05-25", "SendSms",
                "cn-hangzhou", "test");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        return request;
    }

    public void dXinBaoSendMessage(String phone, String content) {
        String httpArgs = "u=" + USERNAME + "&" +
                "p=" + md5(PASSWORD) + "&" +
                "m=" + phone + "&" +
                "c=" + encodeUrlString(content, "UTF-8");
        request(HTTP_URL, httpArgs);
        log.info("正在发送短信至：" + phone + " ====>> " + "内容为：" + content);
    }

    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null) {
            return str;
        }
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }
}
