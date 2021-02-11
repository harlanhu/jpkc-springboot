package com.study.jpkc.controller;

import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.lang.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/12/22
 */
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    private final KaptchaComponent kaptchaComponent;

    public VerifyCodeController(KaptchaComponent kaptchaComponent) {
        this.kaptchaComponent = kaptchaComponent;
    }

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
