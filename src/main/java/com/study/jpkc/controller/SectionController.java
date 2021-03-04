package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.ISectionService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    private final ISectionService sectionService;

    public SectionController(ISectionService sectionService) {
        this.sectionService = sectionService;
    }

    @RequiresUser
    @PostMapping("/save/{courseId}")
    public Result saveSection(@PathVariable String courseId, @RequestParam String sectionName, @RequestParam String sectionDesc, @RequestParam MultipartFile[] sectionFiles) {
        System.out.println(courseId);
        System.out.println(sectionName);
        System.out.println(sectionDesc);
        System.out.println(Arrays.toString(sectionFiles));
        return Result.getSuccessRes(null);
    }

}
