package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.School;
import com.study.jpkc.service.ISchoolService;
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
@RequestMapping("/school")
public class SchoolController {

    private final ISchoolService schoolService;

    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/getSchoolById/{schoolId}")
    public Result getSchoolById(@PathVariable String schoolId) {
        return Result.getSuccessRes(schoolService.getById(schoolId));
    }

    @GetMapping(value = "/getByName")
    public Result getSchoolByName(@RequestParam String schoolName) {
        return Result.getSuccessRes(schoolService.getOne(new QueryWrapper<School>().eq("school_name", schoolName)));
    }

    @GetMapping("/getByTeacherId/{teacherId}")
    public Result getByTeacherId(@PathVariable String teacherId) {
        return Result.getSuccessRes(schoolService.getByTeacherId(teacherId));
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.getSuccessRes(schoolService.list());
    }

    @GetMapping("/getByCourseId/{courseId}")
    public Result getByCourseId(@PathVariable String courseId) {
        return Result.getSuccessRes(schoolService.getByCourseId(courseId));
    }

    @GetMapping("/getCount")
    public Result getCount() {
        return Result.getSuccessRes(schoolService.count());
    }

    @GetMapping("/getNameByResourceId/{resourceId}")
    public Result getNameByResourceId(@PathVariable String resourceId) {
        return Result.getSuccessRes(schoolService.getNameByResourceId(resourceId));
    }

}
