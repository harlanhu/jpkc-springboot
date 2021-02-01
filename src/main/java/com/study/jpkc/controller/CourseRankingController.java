package com.study.jpkc.controller;

import com.study.jpkc.common.lang.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Harlan
 * @Date 2021/2/1
 * @Desc 课程排行控制器
 */
@RestController("/courseRanking")
public class CourseRankingController {

    @GetMapping("/getWeekTop")
    public Result getWeekTop() {
        return null;
    }
}
