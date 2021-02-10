package com.study.jpkc.mapper;

import com.study.jpkc.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/2/6 15:38
 * @desc
 */
@SpringBootTest
class TeacherMapperTest {

    @Autowired
    TeacherMapper teacherMapper;

    @Test
    void selectOneByCourseId() {
        Teacher teacher = teacherMapper.selectOneByCourseId("0cff17ee22f957b9aa6ea2390ca4f6e0");
        System.out.println(teacher);
    }
}
