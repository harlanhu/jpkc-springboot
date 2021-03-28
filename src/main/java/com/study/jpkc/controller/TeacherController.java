package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.constant.TeacherConstant;
import com.study.jpkc.common.dto.TeacherDto;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.School;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.service.ISchoolService;
import com.study.jpkc.service.ITeacherService;
import com.study.jpkc.shiro.AccountProfile;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
@RequestMapping("/teacher")
public class TeacherController {

    private final ITeacherService teacherService;

    private final ISchoolService schoolService;

    public TeacherController(ITeacherService teacherService, ISchoolService schoolService) {
        this.teacherService = teacherService;
        this.schoolService = schoolService;
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
        Page<Teacher> page = teacherService.page(new Page<>(current, size));
        List<Teacher> teacherList = page.getRecords();
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        teacherList.forEach(teacher -> {
            School school = schoolService.getByTeacherId(teacher.getTeacherId());
            TeacherDto teacherDto = BeanUtil.toBean(teacher, TeacherDto.class);
            teacherDto.setSchool(school);
            teacherDtoList.add(teacherDto);
        });
        Page<TeacherDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageDto.setRecords(teacherDtoList);
        pageDto.setPages(page.getPages());
        return Result.getSuccessRes(PageVo.getPageVo(pageDto));
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

    @SneakyThrows
    @PostMapping("/save")
    public Result save(@RequestParam String teacherJsonStr, @RequestParam(required = false) MultipartFile avatarFile) {
        Teacher teacher = JSON.parseObject(teacherJsonStr, Teacher.class);
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        teacher.setUserId(account.getUserId());
        String teacherId = teacherService.save(teacher, avatarFile);
        if (ObjectUtil.isNull(teacherId)) {
            return Result.getFailRes("教师资格申请失败");
        }
        return Result.getSuccessRes(teacherId, "教师资格申请成功");
    }

    @GetMapping("/isTeacher")
    public Result isTeacher() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("user_id", account.getUserId()));
        return Result.getSuccessRes(ObjectUtil.isNotNull(teacher));
    }

    @GetMapping("/getByUser")
    public Result getByUser() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq(TeacherConstant.COL_USERID, account.getUserId()));
        TeacherDto teacherDto = BeanUtil.copyProperties(teacher, TeacherDto.class);
        teacherDto.setSchool(schoolService.getByTeacherId(teacher.getTeacherId()));
        return Result.getSuccessRes(teacherDto);
    }
}
