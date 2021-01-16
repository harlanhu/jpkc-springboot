package com.study.jpkc.mapper;

import com.study.jpkc.entity.WebsiteResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/1/16 19:21
 * @desc 网站资源dao测试
 */
@SpringBootTest
public class WebResourceMapperTest {

    @Autowired
    WebsiteResourceMapper websiteResourceMapper;

    @Test
    void selectWebResourceByLayoutNameTest() {
        List<WebsiteResource> websiteResources = websiteResourceMapper.selectWebResourceByLayoutName("home-banner-carousel");
        System.out.println(websiteResources);
    }
}
