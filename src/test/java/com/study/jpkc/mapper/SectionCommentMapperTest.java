package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.SectionComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/22
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SectionCommentMapperTest {

    @Autowired
    SectionCommentMapper sCommentMapper;

    @Test
    void selectBySectionIdWithNewTest() {
        Page<SectionComment> page = sCommentMapper.selectBySectionIdWithNew("16a9c3e2e27a4eeca4d88b584c784670", new Page<>(1, 3));
        assertThat(page.getRecords()).isNotNull();
        System.out.println(page.getRecords());
    }
}
