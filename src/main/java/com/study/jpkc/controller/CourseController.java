package com.study.jpkc.controller;


import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.utils.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    private final ICourseService courseService;

    private final RedisUtils redisUtils;

    public CourseController(ICourseService courseService, RedisUtils redisUtils) {
        this.courseService = courseService;
        this.redisUtils = redisUtils;
    }

    @RequiresGuest
    @GetMapping("/getAllCourses")
    public Result getAllCourses() {
        List<Course> courses = courseService.selectAllCourse();
        return Result.getSuccessRes(courses);
    }

    @GetMapping("/getCourseByUserId/{userId}")
    public Result getCoursesByUserId(@PathVariable String userId) {
        List<Course> courses = courseService.findCourseByUserId(userId);
        return Result.getSuccessRes(courses);
    }

    @GetMapping("/getCourseById/{courseId}")
    public Result getCourseById(@PathVariable String courseId) {
        return Result.getSuccessRes(courseService.getById(courseId));
    }

    @GetMapping("/getRanking/{current}/{size}")
    public Result getRanking(@PathVariable int current, @PathVariable int size) {
        int topTotal = Math.toIntExact(redisUtils.getListLength("courseWeekTop"));
        if (current - 1 > topTotal / size) {
            return Result.getSuccessRes(null);
        }
        List<Course> courseRanking = courseService.getRanking(current, size);
        return Result.getSuccessRes(
                new PageVo(courseRanking, ((Integer) current).longValue(),
                        ((Integer) size).longValue(), (long) topTotal,
                        ((Integer) (topTotal / size + 1)).longValue()));
    }
}
