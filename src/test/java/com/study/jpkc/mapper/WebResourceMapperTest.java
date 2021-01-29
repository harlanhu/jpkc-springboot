package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.WebsiteResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Page<WebsiteResource> page = new Page<>(2, 2);
        IPage<WebsiteResource> resourceIPage = websiteResourceMapper.selectWebResourceByLayoutName(page, "home-banner-carousel");
        System.out.println(resourceIPage.getRecords());
    }
}
