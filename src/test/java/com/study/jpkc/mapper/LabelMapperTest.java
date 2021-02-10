package com.study.jpkc.mapper;

import com.study.jpkc.entity.Label;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2020/12/18
 */
@SpringBootTest
class LabelMapperTest {

    @Autowired
    LabelMapper labelMapper;

    @Test
    void InsertTest() {
        Label label = new Label();
        label.setLabelId(UUID.randomUUID().toString().replace("-", ""));
        label.setLabelName("测试标签");
        label.setLabelDesc("测试标签描述");
        labelMapper.insert(label);
    }

    @Test
    void selectTest() {
        List<Label> labels = labelMapper.selectAll();
        System.out.println(labels);
    }
}
