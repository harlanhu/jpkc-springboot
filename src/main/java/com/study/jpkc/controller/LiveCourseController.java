package com.study.jpkc.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.LiveCourse;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.service.ILiveCourseService;
import com.study.jpkc.service.ITeacherService;
import com.study.jpkc.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private final ITeacherService teacherService;

    public LiveCourseController(ILiveCourseService liveCourseService, ITeacherService teacherService) {
        this.liveCourseService = liveCourseService;
        this.teacherService = teacherService;
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

    @GetMapping("/getByUser")
    public Result getByUser() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        List<LiveCourse> liveCourseList = liveCourseService.getByUserId(account.getUserId());
        return Result.getSuccessRes(liveCourseList);
    }

    @PostMapping("/create")
    public Result create(@RequestParam String lCourseStr, @RequestParam MultipartFile logoFile) {
        LiveCourse lCourse = JSON.parseObject(lCourseStr, LiveCourse.class);
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("user_id", account.getUserId()));
        boolean isSuccess = liveCourseService.save(teacher.getTeacherId(), lCourse, logoFile);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/remove/{lCourseId}")
    public Result remove(@PathVariable String lCourseId) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("user_id", account.getUserId()));
        LiveCourse liveCourse = liveCourseService.getById(lCourseId);
        if (teacher.getTeacherId().equals(liveCourse.getTeacherId())) {
            liveCourseService.removeById(lCourseId);
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/getById/{lCourseId}")
    public Result getById(@PathVariable("lCourseId") String lCourseId) {
        LiveCourse lCourse = liveCourseService.getById(lCourseId);
        return Result.getSuccessRes(lCourse);
    }

    @GetMapping("/getByLiving/{current}/{size}")
    public Result getByLiving(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(
                liveCourseService.page(
                        new Page<>(current, size), new QueryWrapper<LiveCourse>().eq("status", 1))));
    }
}
