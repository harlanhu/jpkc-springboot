package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.IMajorService;
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
@RequestMapping("/major")
public class MajorController {

    private final IMajorService majorService;

    public MajorController(IMajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.getSuccessRes(majorService.list());
    }

    @GetMapping("/getById/{majorId}")
    public Result getById(@PathVariable String majorId) {
        return Result.getSuccessRes(majorService.getById(majorId));
    }
}
