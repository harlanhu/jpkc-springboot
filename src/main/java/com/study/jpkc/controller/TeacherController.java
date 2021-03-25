package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/getOneByCourseId/{courseId}")
    public Result getOneByCourseId(@PathVariable String courseId) {
        return Result.getSuccessRes(teacherService.getByCourseId(courseId));
    }

    @GetMapping("/getOneById/{teacherId}")
    public Result getOneById(@PathVariable String teacherId) {
        return Result.getSuccessRes(teacherService.getById(teacherId));
    }

    @GetMapping("/getBySchoolId/{schoolId}/{current}/{size}")
    public Result getBySchoolId(@PathVariable String schoolId, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(teacherService.page(new Page<>(current, size),
                new QueryWrapper<Teacher>().eq("school_id", schoolId))));
    }

    @GetMapping("/getAll/{current}/{size}")
    public Result getAll(@PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(teacherService.page(new Page<>(current, size))));
    }

    @GetMapping("/getByName/{teacherName}/{current}/{size}")
    public Result getByName(@PathVariable String teacherName, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(teacherService.page(new Page<>(current, size),
                new QueryWrapper<Teacher>().eq("teacher_name", teacherName))));
    }

    @GetMapping("/getByUserId/{userId}")
    public Result getByUserId(@PathVariable String userId) {
        return Result.getSuccessRes(teacherService.getOne(new QueryWrapper<Teacher>().eq("user_id", userId)));
    }

    @GetMapping("/save")
    public Result save(@RequestBody Teacher teacher) {
        //TODO: 教师注册
        return null;
    }
}
