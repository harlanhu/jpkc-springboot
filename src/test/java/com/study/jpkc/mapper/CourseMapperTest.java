package com.study.jpkc.mapper;

import com.study.jpkc.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/1/24 21:52
 * @desc 课程持久层测试
 */
@SpringBootTest
class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;

    @Test
    void selectCourseByUserIdTest() {
        List<Course> courses = courseMapper.selectCourseByUserId("871444cac40c4ec09f83b920f6f34c00");
        assertThat(courses).isNotNull();
    }
}
