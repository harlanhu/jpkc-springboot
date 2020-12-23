package com.study.jpkc.controller;

import com.study.jpkc.common.component.SmsComponent;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/12/23
 * @desc 手机验证码
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;

    /**
     * 获取短信验证码接口
     * @param phone 手机号
     * @return 返回信息
     */
    @PostMapping("getSmsVerifyCode")
    public Result getSmsVerifyCode(@RequestBody String phone) {
        if (!RegexUtils.phoneMatches(phone)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PHONE);
        }
        //发送短信
        String res = smsComponent.sendVerifyCodeSms(phone);
        if (res == null) {
            return Result.getFailRes(SmsComponent.FAIL_SEND_MESSAGE);
        }
        return Result.getSuccessRes(null, SmsComponent.SUCCESS_SEND_MESSAGE);
    }
}
