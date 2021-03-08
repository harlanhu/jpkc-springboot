package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.dto.CourseDto;
import com.study.jpkc.common.dto.SectionDto;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.*;
import com.study.jpkc.service.*;
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
import java.util.Map;

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

    private final ISectionService sectionService;

    private final IResourceService resourceService;

    private final ITeacherService teacherService;

    private final ISchoolService schoolService;

    public CourseController(ICourseService courseService,
                            RedisUtils redisUtils,
                            ICategoryService categoryService,
                            ILabelService labelService,
                            ISectionService sectionService,
                            IResourceService resourceService,
                            ITeacherService teacherService,
                            ISchoolService schoolService) {
        this.courseService = courseService;
        this.redisUtils = redisUtils;
        this.categoryService = categoryService;
        this.labelService = labelService;
        this.sectionService = sectionService;
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.schoolService = schoolService;
    }

    @GetMapping("/getAllOpen/{current}/{size}")
    public Result getAllCourses(@PathVariable Integer current, @PathVariable Integer size) {
        Page<Course> page = courseService.page(new Page<>(current, size), new QueryWrapper<Course>().eq("course_status", 0));
        return Result.getSuccessRes(PageVo.getPageVo(page));
    }

    @GetMapping("/getCourseByUserId/{userId}")
    public Result getCoursesByUserId(@PathVariable String userId) {
        List<Course> courses = courseService.findCourseByUserId(userId);
        List<CourseDto> detailsDtoList = new ArrayList<>();
        for (Course course : courses) {
            CourseDto detailsDto = new CourseDto();
            BeanUtil.copyProperties(course, detailsDto);
            detailsDto.setCategoryList(categoryService.getByCourseId(course.getCourseId()));
            detailsDto.setLabelList(labelService.getByCourseId(course.getCourseId()));
            detailsDtoList.add(detailsDto);
        }
        return Result.getSuccessRes(detailsDtoList);
    }

    @GetMapping("/getCourseById/{courseId}")
    public Result getCourseById(@PathVariable String courseId) {
        Course course  =  courseService.getById(courseId);
        Teacher teacher = teacherService.getById(course.getTeacherId());
        School school = schoolService.getByTeacherId(course.getTeacherId());
        List<Category> categoryList = categoryService.getByCourseId(courseId);
        List<Label> labelList = labelService.getByCourseId(courseId);
        List<SectionDto> sectionDtoList = new ArrayList<>();
        SectionDto sectionDto = null;
        List<Map<Section, List<Resource>>> sectionDetailList = sectionService.getDetailByCourseId(courseId);
        for (Map<Section, List<Resource>> sectionListMap : sectionDetailList) {
            for (Section section : sectionListMap.keySet()) {
                sectionDto = BeanUtil.copyProperties(section, SectionDto.class);
                sectionDto.setResources(sectionListMap.get(section));
                sectionDtoList.add(sectionDto);
            }
        }
        CourseDto courseDto = BeanUtil.copyProperties(course, CourseDto.class);
        courseDto.setTeacher(teacher);
        courseDto.setSchool(school);
        courseDto.setCategoryList(categoryList);
        courseDto.setLabelList(labelList);
        courseDto.setSectionDtoList(sectionDtoList);
        return Result.getSuccessRes(courseDto);
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

    @PostMapping("/getByName")
    public Result getByName(@RequestBody String courseName) {
        Course course = courseService.getOne(new QueryWrapper<Course>().eq("course_name", courseName));
        List<Section> sectionList = sectionService.list(new QueryWrapper<Section>().eq("course_id", course.getCourseId()));
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Section section : sectionList) {
            List<Resource> resourceList = resourceService.list(new QueryWrapper<Resource>().eq("section_id", section.getSectionId()));
            SectionDto sectionDto = BeanUtil.toBean(section, SectionDto.class);
            sectionDto.setResources(resourceList);
            sectionDtoList.add(sectionDto);
        }
        CourseDto courseDto = BeanUtil.toBean(course, CourseDto.class);
        courseDto.setSectionDtoList(sectionDtoList);
        return Result.getSuccessRes(courseDto);
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
        return Result.getFailRes("上传失败！");
    }
}
