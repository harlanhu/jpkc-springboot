package com.study.jpkc.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Student;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IStudentService;
import com.study.jpkc.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final IStudentService studentService;

    private final IUserService userService;

    public StudentController(IStudentService studentService, IUserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/getAll/{current}/{size}")
    public Result getAll(@PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(studentService.getAll(current, size)));
    }

    @GetMapping("/getByName/{studentName}/{current}/{size}")
    public Result getByName(@PathVariable String studentName, @PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(studentService.page(new Page<>(current, size),
                new QueryWrapper<Student>().eq("student_name", studentName))));
    }

    @PostMapping("/save")
    public Result save(@RequestBody Student student) {
        User user = new User();
        String userId = IdUtil.simpleUUID();
        user.setUserId(userId);
        user.setPassword("Aa123456");
        user.setUserSex(0);
        user.setUserCreated(LocalDateTime.now());
        user.setUserStatus(0);
        student.setStudentId(IdUtil.simpleUUID());
        student.setUserId(userId);
        userService.save(user);
        studentService.save(student);
        return Result.getSuccessRes(null);
    }
}
