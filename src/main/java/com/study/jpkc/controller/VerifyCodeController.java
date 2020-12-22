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
 * @Date 2020/12/22
 */
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    private SmsComponent smsComponent;

    @PostMapping("getSmsVerifyCode")
    public Result getSmsVerifyCode(@RequestBody String phone) {
        if (!RegexUtils.phoneMatches(phone)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PHONE);
        }
        if (smsComponent.sendVerifyCodeSms(phone) == null) {
            return Result.getFailRes(SmsComponent.FAIL_SEND_MESSAGE);
        }
        return Result.getSuccessRes(null, SmsComponent.SUCCESS_SEND_MESSAGE);
    }
}
