package com.study.jpkc.controller;


import com.study.jpkc.common.constant.LayoutConstant;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.School;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.service.ISchoolService;
import com.study.jpkc.service.IWebsiteLayoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
@RestController
@RequestMapping("/website-layout")
public class WebsiteLayoutController {

    private final IWebsiteLayoutService layoutService;

    private final ISchoolService schoolService;

    private final ICourseService courseService;

    public WebsiteLayoutController(IWebsiteLayoutService layoutService, ISchoolService schoolService, ICourseService courseService) {
        this.layoutService = layoutService;
        this.schoolService = schoolService;
        this.courseService = courseService;
    }

    @GetMapping("/getById/{layoutId}/{current}/{size}")
    public Result getById(@PathVariable String layoutId, @PathVariable Integer current, @PathVariable Integer size) {
        if (LayoutConstant.SCHOOL_LAYOUT.equals(layoutId)) {
            return Result.getSuccessRes(schoolService.getSchoolByLayout(LayoutConstant.SCHOOL_LAYOUT));
        } else {
            return Result.getSuccessRes(PageVo.getPageVo(courseService.getByLayout(layoutId, current, size)));
        }
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return  Result.getSuccessRes(layoutService.list());
    }

    @GetMapping("/getSchool")
    public Result getSchool() {
        List<School> schoolList = schoolService.getSchoolByLayout(LayoutConstant.SCHOOL_LAYOUT);
        return Result.getSuccessRes(schoolList);
    }

    @GetMapping("/getRecommend/{current}/{size}")
    public Result getRecommend(@PathVariable Integer current, @PathVariable Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.getByLayout(LayoutConstant.RECOMMEND_LAYOUT, current, size)));
    }

    @GetMapping("/getAdvertising/{current}/{size}")
    public Result getAdvertising(@PathVariable Integer current, @PathVariable Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.getByLayout(LayoutConstant.ADVERTISING_LAYOUT, current, size)));
    }

    @GetMapping("/getCarousel/{current}/{size}")
    public Result getCarousel(@PathVariable Integer current, @PathVariable Integer size) {
        return Result.getSuccessRes(PageVo.getPageVo(courseService.getByLayout(LayoutConstant.CAROUSEL_LAYOUT, current, size)));
    }

    @GetMapping("/bindCourse/{layoutId}/{courseId}")
    public Result bindCourse(@PathVariable String layoutId, @PathVariable String courseId) {
        boolean isSuccess = layoutService.bindCourse(layoutId, courseId);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/bindSchool/{layoutId}/{schoolId}")
    public Result bindSchool(@PathVariable String layoutId, @PathVariable String schoolId) {
        boolean isSuccess = layoutService.bindSchool(layoutId, schoolId);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/unbindCourse/{layoutId}/{courseId}")
    public Result unbindCourse(@PathVariable String layoutId, @PathVariable String courseId) {
        boolean isSuccess = layoutService.unbindCourse(layoutId, courseId);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/unbindSchool/{layoutId}/{schoolId}")
    public Result unbindSchool(@PathVariable("layoutId") String layoutId, @PathVariable("schoolId") String schoolId) {
        boolean isSuccess = layoutService.unbindSchool(layoutId, schoolId);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }
}
