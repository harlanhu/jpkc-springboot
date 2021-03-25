package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
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

    @GetMapping("/getAllByPage/{current}/{size}")
    public Result getAllByPage(@PathVariable Integer current, @PathVariable Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(schoolService.page(new Page<>(current, size))));
    }

    @GetMapping("/getAllWithoutLayout/{layoutId}/{current}/{size}")
    public Result getAllWithoutLayout(@PathVariable String layoutId, @PathVariable Integer current, @PathVariable Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(schoolService.getAllWithoutLayout(layoutId, current, size)));
    }

    @PostMapping("/query/{current}/{size}")
    public Result query(@RequestBody String keyWords, @PathVariable Integer current, @PathVariable Integer size) {
        //TODO: ES模糊搜索
        return null;
    }
}
