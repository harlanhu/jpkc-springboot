package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.Course;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;

    @Test
    void selectCourseByUserIdTest() {
        List<Course> courses = courseMapper.selectCourseByUserId("871444cac40c4ec09f83b920f6f34c00");
        assertThat(courses).isNotNull();
    }

    @Test
    void selectCourseByCategoryId() {
        Page<Course> pageInfo = courseMapper.selectByCategoryId(new Page<>(1, 2), "120a805c5fa05a0c975c1222b81a8204");
        assertThat(pageInfo).isNotNull();
        log.info(pageInfo.getRecords().get(0).toString());
    }
}
