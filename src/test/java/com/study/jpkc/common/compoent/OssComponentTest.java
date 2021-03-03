package com.study.jpkc.common.compoent;

import com.aliyun.oss.model.PutObjectResult;
import com.study.jpkc.common.component.OssComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Author Harlan
 * @Date 2020/12/30
 */
@SpringBootTest
class OssComponentTest {

    @Autowired
    OssComponent ossComponent;

    @Test
    void uploadTest() {
        PutObjectResult testRes = ossComponent.upload("testObj/first", "Hello OSS!");
        System.out.println(testRes);
    }

    @Test
    void downloadTest() throws IOException {
        ossComponent.download("testObj");
    }
}
