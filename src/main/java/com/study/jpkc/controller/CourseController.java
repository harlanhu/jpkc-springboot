package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICourseService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

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
}
