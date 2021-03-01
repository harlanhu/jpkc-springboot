package com.study.jpkc.mapper;

import com.study.jpkc.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/1
 */
@SpringBootTest
@Slf4j
class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void selectByCourseIdTest() {
        List<Category> categories = categoryMapper.selectByCourseId("31a4dfebbea1596993fc643611e407aa");
        System.out.println(categories);
        assertThat(categories).isNotNull();
    }
}
