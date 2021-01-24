package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.IWebsiteResourceService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/getWebResourceByLayoutName")
    public Result getWebResourceByLayoutName(String layoutName) {
        return Result.getSuccessRes(websiteResourceService.findWebResourceByLayoutName(layoutName));
    }
}
