package com.study.jpkc.common.compoent;

import com.study.jpkc.common.component.OssComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        URL testRes = ossComponent.upload("testObj/first.text", "Hello OSS!");
        assertThat(testRes).isNotNull();
        System.out.println(testRes);
    }

    @Test
    void downloadTest() throws IOException {
        ossComponent.download("testObj");
    }
}
