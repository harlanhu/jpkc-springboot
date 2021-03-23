package com.study.jpkc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
        IPage<Course> page = courseService.getOpenByType(1, 4, CourseConstant.COURSE_POPULAR);
        List<Course> courseList = page.getRecords();
        assertThat(courseList).isNotNull();
        courseList.forEach(System.out::println);
    }

    @Test
    void getOpenByTypeAndCategoryTest() {
        IPage<Course> courseIPage = courseService.getOpenByTypeAndCategory(1, 4, CourseConstant.COURSE_POPULAR, "0");
        List<Course> courseList = courseIPage.getRecords();
        assertThat(courseList).isNotNull();
        courseList.forEach(System.out::println);
    }

    @Test
    void getOpenByTypeAndSchoolTest() {
        IPage<Course> courseIPage = courseService.getOpenByTypeAndSchool(1,4, CourseConstant.COURSE_POPULAR, "25c2e96a5ac359dfba9fe46246abc9f6");
        List<Course> courseList = courseIPage.getRecords();
        assertThat(courseList).isNotNull();
        courseList.forEach(System.out::println);
    }
}
