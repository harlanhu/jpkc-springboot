package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.service.IWebsiteLayoutService;
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
@RequestMapping("/website-layout")
public class WebsiteLayoutController {

    private final IWebsiteLayoutService layoutService;

    public WebsiteLayoutController(IWebsiteLayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return  Result.getSuccessRes(layoutService.list());
    }
}
