package com.study.jpkc.utils;

import com.study.jpkc.common.constant.MailConstant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/2/10 0:20
 * @desc 邮件工具类
 */
public class MailUtils {

    private MailUtils() {
        throw new IllegalArgumentException("工具类不能被实例化");
    }

    /**
     * 获取注册邮件文本
     * @param userEmail 用户邮箱
     * @param activateUrl 激活链接
     * @param userCreated 注册时间
     * @return 文本
     */
    public static String getRegisterMailBody(String userEmail, String activateUrl, LocalDateTime userCreated) {
        return MailConstant.REGISTER_MAIL_TEMPLATE.replace("${userEmail}", userEmail)
                .replace("${activateUrl}", activateUrl)
                .replace("${userCreated}", userCreated.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
