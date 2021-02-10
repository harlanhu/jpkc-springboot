package com.study.jpkc.service;

import com.study.jpkc.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/1/6
 */
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    ICategoryService categoryService;

    @Test
    void findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        assert  (categories != null);
        System.out.println(categories);
    }

    @Test
    void findRootCategory() {
        List<Category> categories = categoryService.findRootCategories();
        assert (categories != null);
        System.out.println(categories);
    }
}
