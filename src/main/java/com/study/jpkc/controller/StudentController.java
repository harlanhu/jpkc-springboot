package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Student;
import com.study.jpkc.service.IStudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
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
}
