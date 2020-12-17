package com.study.jpkc.controller;

import com.study.jpkc.common.lang.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/12/17
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result hello() {
        return Result.getFailRes();
    }
}
