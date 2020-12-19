package com.study.jpkc.controller;

import com.study.jpkc.common.lang.Result;
import com.study.jpkc.shiro.AccountProfile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 15:02
 * @desc
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public Result login(@RequestBody AccountProfile account) {
        return Result.getSuccessRes(account);
    }
}
