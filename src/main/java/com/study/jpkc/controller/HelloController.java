package com.study.jpkc.controller;

import com.study.jpkc.common.ResultBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2020/12/17
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResultBean hello() {
        return ResultBean.getFailRes();
    }
}
