package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Category;
import com.study.jpkc.service.ICategoryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/getAllCategory")
    public Result getAllCategory() {
        List<Category> categories = categoryService.findAllCategories();
        return Result.getSuccessRes(categories);
    }

    @GetMapping("/getRootCategory")
    public Result getRootCategory() {
        List<Category> categories = categoryService.findRootCategories();
        return Result.getSuccessRes(categories);
    }
}
