package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.ISchoolService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;

    @RequiresGuest
    @GetMapping("/getSchoolById/{schoolId}")
    public Result getSchoolById(@PathVariable String schoolId) {
        return Result.getSuccessRes(schoolService.getById(schoolId));
    }

}
