package com.study.jpkc.controller;

import com.study.jpkc.common.component.SmsComponent;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.utils.RegexUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final SmsComponent smsComponent;

    public SmsController(SmsComponent smsComponent) {
        this.smsComponent = smsComponent;
    }

    /**
     * 获取短信验证码接口
     * @param phone 手机号
     * @return 返回信息
     */
    @GetMapping("getSmsVerifyCode/{phone}")
    public Result getSmsVerifyCode(@PathVariable String phone) {
        if (!RegexUtils.phoneMatches(phone)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PHONE);
        }
        //发送短信
        String res = smsComponent.sendSmsVerifyCode(phone);
        if (res == null) {
            return Result.getFailRes(SmsComponent.FAIL_SEND_MESSAGE);
        }
        return Result.getSuccessRes(null, SmsComponent.SUCCESS_SEND_MESSAGE);
    }
}
