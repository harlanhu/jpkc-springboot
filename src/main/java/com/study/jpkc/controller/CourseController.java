package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.task.CourseScheduleTask;
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
        int total = Math.toIntExact(redisUtils.getListLength(CourseScheduleTask.COURSE_TOP_50_KEY));
        if (current - 1 > total / size) {
            return Result.getSuccessRes(null);
        }
        List<Course> courseRanking = courseService.getRanking(current, size);
        return Result.getSuccessRes(
                new PageVo(courseRanking, ((Integer) current).longValue(),
                        ((Integer) size).longValue(), (long) total,
                        ((Integer) (total / size + 1)).longValue()));
    }

    @GetMapping("/getNew/{current}/{size}")
    public Result getNew(@PathVariable int current, @PathVariable int size) {
        int total = Math.toIntExact(redisUtils.getListLength(CourseScheduleTask.COURSE_NEW_KEY));
        if (current - 1 > total / size) {
            return Result.getSuccessRes(null);
        }
        List<Course> coursesNew = courseService.getNew(current, size);
        return Result.getSuccessRes(
                new PageVo(coursesNew, ((Integer) current).longValue(),
                        ((Integer) size).longValue(), (long) total,
                        ((Integer) (total / size + 1)).longValue()));
    }

    @GetMapping("/getStar/{current}/{size}")
    public Result getStar(@PathVariable int current, @PathVariable int size) {
        int total = Math.toIntExact(redisUtils.getListLength(CourseScheduleTask.COURSE_STAR_KEY));
        if (current - 1 > total / size) {
            return Result.getSuccessRes(null);
        }
        List<Course> coursesNew = courseService.getStar(current, size);
        return Result.getSuccessRes(
                new PageVo(coursesNew, ((Integer) current).longValue(),
                        ((Integer) size).longValue(), (long) total,
                        ((Integer) (total / size + 1)).longValue()));
    }

    @GetMapping("/getBySchoolId/{schoolId}/{current}/{size}")
    public Result getBySchoolId(@PathVariable String schoolId, @PathVariable int current, @PathVariable int size) {
        Page<Course> coursePage = courseService.page(new Page<>(current, size), new QueryWrapper<Course>().eq("school_Id", schoolId));
        return Result.getSuccessRes(PageVo.getPageVo(coursePage));
    }

    @GetMapping("/getByTeacherId/{teacherId}/{current}/{size}")
    public Result getByTeacherId(@PathVariable String teacherId, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.page(new Page<>(current, size),
                new QueryWrapper<Course>().eq("teacher_id", teacherId))));
    }

    @GetMapping("/getByLabelId/{labelId}/{current}/{size}")
    public Result getByLabelId(@PathVariable String labelId, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.getByLabelId(labelId, current, size)));
    }

    @GetMapping("/getByCategoryId/{categoryId}/{current}/{size}")
    public Result getByCategoryId(@PathVariable String categoryId, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.getByCategoryId(categoryId, current, size)));
    }
}
