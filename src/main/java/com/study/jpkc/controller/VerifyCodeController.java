package com.study.jpkc.controller;

import com.study.jpkc.common.component.KaptchaComponent;
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

    @GetMapping("/getCode")
    public void render() {
        kaptchaComponent.render();
    }

    @PostMapping("/validCode")
    public void validCustomTime(@RequestParam String code) {
        kaptchaComponent.validate(code);
    }
}
