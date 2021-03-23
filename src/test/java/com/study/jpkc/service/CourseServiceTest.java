package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.constant.CourseConstant;
import com.study.jpkc.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/23
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseServiceTest {

    @Autowired
    ICourseService courseService;

    @Test
    void getOpenByTypeTest() {
        Page<Course> page = courseService.getOpenByType(1, 4, CourseConstant.COURSE_POPULAR);
        List<Course> courseList = page.getRecords();
        assertThat(courseList).isNotNull();
        courseList.forEach(course -> System.out.println(course));
    }
}
