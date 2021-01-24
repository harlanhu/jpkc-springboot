package com.study.jpkc.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/1/24 21:52
 * @desc 课程持久层测试
 */
@SpringBootTest
public class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;

    @Test
    void selectCourseByUserIdTest() {
        System.out.println(courseMapper.selectCourseByUserId("871444cac40c4ec09f83b920f6f34c00"));
    }
}
