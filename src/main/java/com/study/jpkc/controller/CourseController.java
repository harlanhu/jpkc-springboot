package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.dto.CourseDetailsDto;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICategoryService;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.service.ILabelService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.task.CourseScheduleTask;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.RedisUtils;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    private final ICategoryService categoryService;

    private final ILabelService labelService;

    private final RedisUtils redisUtils;

    public CourseController(ICourseService courseService, RedisUtils redisUtils, ICategoryService categoryService, ILabelService labelService) {
        this.courseService = courseService;
        this.redisUtils = redisUtils;
        this.categoryService = categoryService;
        this.labelService = labelService;
    }

    @GetMapping("/getAllCourses")
    public Result getAllCourses() {
        List<Course> courses = courseService.selectAllCourse();
        return Result.getSuccessRes(courses);
    }

    @GetMapping("/getCourseByUserId/{userId}")
    public Result getCoursesByUserId(@PathVariable String userId) {
        List<Course> courses = courseService.findCourseByUserId(userId);
        List<CourseDetailsDto> detailsDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDetailsDto detailsDto = new CourseDetailsDto();
            BeanUtil.copyProperties(course, detailsDto);
            detailsDto.setCategoryList(categoryService.getByCourseId(course.getCourseId()));
            detailsDto.setLabelList(labelService.getByCourseId(course.getCourseId()));
            detailsDtoList.add(detailsDto);
        }
        return Result.getSuccessRes(detailsDtoList);
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

    @SneakyThrows
    @RequiresUser
    @PostMapping("/create")
    public Result create(@RequestParam String courseJsonStr, @RequestParam(required = false) MultipartFile logoFile, @RequestParam String categoryId, @RequestParam String labelNames) {
        Course course = JSON.parseObject(courseJsonStr, Course.class);
        String[] labelNamesAr = JSON.parseObject(labelNames, String[].class);
        AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        String courseId = courseService.save(accountProfile, course, logoFile, categoryId, labelNamesAr);
        if (ObjectUtil.isNull(courseId)) {
            return Result.getFailRes("课程创建失败");
        }
        return Result.getSuccessRes(courseId, "课程创建成功");
    }

    @SneakyThrows
    @RequiresUser
    @PostMapping("/uploadLogo/{courseId}")
    public Result uploadLogo(@PathVariable String courseId, @RequestBody MultipartFile logoFile) {
        if (!FileUtils.isTypeOfPicture(logoFile)) {
            return Result.getFailRes("文件格式不正确！");
        }
        if (Boolean.TRUE.equals(courseService.uploadLogo(courseId, logoFile))) {
            return Result.getSuccessRes("上传成功!");
        }
        return Result.getFailRes();
    }
}
