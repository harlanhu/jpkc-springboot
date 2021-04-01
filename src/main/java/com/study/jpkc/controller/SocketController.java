package com.study.jpkc.controller;

import com.study.jpkc.common.lang.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2021/4/1
 */
@RestController
@RequestMapping("/live")
public class SocketController {

    @PostMapping("/sendMessage")
    public Result sendMessage(@RequestBody String message, @RequestBody String token) {
        return null;
    }
}
