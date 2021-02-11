package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.IClassService;
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
@RequestMapping("/class")
public class ClassController {

    private final IClassService classService;

    public ClassController(IClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/getById/{classId}")
    public Result getById(@PathVariable String classId) {
        return Result.getSuccessRes(classService.getById(classId));
    }

}
