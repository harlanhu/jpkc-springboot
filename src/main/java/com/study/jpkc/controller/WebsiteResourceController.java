package com.study.jpkc.controller;


import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.IWebsiteResourceService;
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
 * @since 2021-01-01
 */
@RestController
@RequestMapping("/website-resource")
public class WebsiteResourceController {

    @Autowired
    private IWebsiteResourceService websiteResourceService;

    @GetMapping("/getWebResourceByLayoutName/{layoutName}")
    public Result getWebResourceByLayoutName(@PathVariable String layoutName) {
        return Result.getSuccessRes(websiteResourceService.findWebResourcesByLayoutName(layoutName));
    }

    @GetMapping("/home/getRecommendResource/{current}/{size}")
    public Result getHomeRecommendResource(@PathVariable int current, @PathVariable("size") int size) {
        return Result.getSuccessRes(PageVo.getPageVo(websiteResourceService.findWebResourcesByLayoutName(current, size, "home-recommend")));
    }
}
