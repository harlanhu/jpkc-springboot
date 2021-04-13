package com.study.jpkc.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/4/13 21:33
 * @desc 公共配置
 */
@Configuration
public class CommonConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 限制上传文件大小
        factory.setMaxFileSize(DataSize.ofGigabytes(2L));
        return factory.createMultipartConfig();
    }

}
