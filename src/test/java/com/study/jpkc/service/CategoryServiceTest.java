package com.study.jpkc.service;

import com.study.jpkc.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author Harlan
 * @Date 2021/1/6
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryServiceTest {

    @Autowired
    ICategoryService categoryService;

    @Test
    void findAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        assertThat(categories).isNotNull();
        System.out.println(categories);
    }

    @Test
    void findRootCategory() {
        List<Category> categories = categoryService.findRootCategories();
        assert (categories != null);
        System.out.println(categories);
    }

    @Test
    void getBranchesByCategoryIdTest() {
        List<Category> categories = categoryService.getBranchesByCategoryId("120a805c5fa05a0c975c1222b81a8204");
        assertThat(categories).isNotNull();
        System.out.println(categories);
    }
}
