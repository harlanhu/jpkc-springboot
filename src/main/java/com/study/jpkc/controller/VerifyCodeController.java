package com.study.jpkc.controller;

import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @PostMapping("/validCode")
    public void validCustomTime(@RequestParam String code) {
        kaptchaComponent.validate(code);
    }

    /**
     * 校验验证码
     * @param coed 验证码
     * @return 返回结果
     */
    @PostMapping("/isValidCode")
    public Result isValidCode(@RequestParam String coed) {
        return Result.getSuccessRes(kaptchaComponent.isValidate(coed));
    }
}
