package com.study.jpkc.controller;


import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.ILiveCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
@RestController
@RequestMapping("/live-course")
public class LiveCourseController {

    private final ILiveCourseService liveCourseService;

    public LiveCourseController(ILiveCourseService liveCourseService) {
        this.liveCourseService = liveCourseService;
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.getSuccessRes(liveCourseService.list());
    }

    @GetMapping("/getAll/{current}/{size}")
    public Result getAll(@PathVariable int current, @PathVariable int size) {
        if (current < 1 || size < 0) {
            return Result.getSuccessRes(null);
        }
        return Result.getSuccessRes(new PageVo(liveCourseService.getLiveCourse(current, size)));
    }

}
