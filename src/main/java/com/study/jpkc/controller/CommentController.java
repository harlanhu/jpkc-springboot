package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Comment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @PostMapping("/save")
    public Result save(@RequestBody Comment comment) {
        return null;
    }

}
