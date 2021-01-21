package com.study.jpkc.controller;

import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Harlan
 * @Date 2020/12/22
 */
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    private KaptchaComponent kaptchaComponent;

    /**
     * 获取验证码
     */
    @GetMapping("/getCode")
    public void render() {
        kaptchaComponent.render();
    }

    /**
     * 校验验证码并删除
     * @param code 验证码
     */
    @GetMapping("/validCode/{code}")
    public void validCustomTime(@PathVariable String code) {
        kaptchaComponent.validate(code);
    }

    /**
     * 校验验证码
     * @param code 验证码
     * @return 返回结果
     */
    @GetMapping("/isValidCode/{code}")
    public Result isValidCode(@PathVariable String code) {
        return Result.getSuccessRes(kaptchaComponent.isValidate(code));
    }
}
