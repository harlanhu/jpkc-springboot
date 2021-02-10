package com.study.jpkc.mapper;

import com.study.jpkc.entity.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Harlan
 * @Date 2021/2/7
 */
@SpringBootTest
public class SchoolMapperTest {

    @Autowired
    SchoolMapper schoolMapper;

    @Test
    void selectByTeacherIdTest() {
        School school = schoolMapper.selectByTeacherId("e72525c1160344e1a0ceb889039d9a02");
        System.out.println(school.getSchoolName());
    }
}
