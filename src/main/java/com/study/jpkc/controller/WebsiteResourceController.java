package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.WebsiteResource;
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

    @GetMapping("/getWebResourceByLayoutName/{layoutName}/{current}/{size}")
    public Result getWebResourceByLayoutName(@PathVariable String layoutName, @PathVariable Integer current, @PathVariable Integer size) {
        IPage<WebsiteResource> wesPage = websiteResourceService.findWebResourceByLayoutName(current, size, layoutName);
        return Result.getSuccessRes(PageVo.getPageVo(wesPage));
    }

    @GetMapping("/getRecommendResource/{current}/{size}")
    public Result getRecommendResource(@PathVariable Integer current, @PathVariable("size") Integer size) {
        return null;
    }
}
