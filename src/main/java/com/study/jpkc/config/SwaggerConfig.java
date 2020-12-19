package com.study.jpkc.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 15:10
 * @desc swagger 配置
 */
@Configuration
@ConfigurationProperties(prefix = "swagger")
@EnableSwagger2
@Setter
public class SwaggerConfig {

    private String basePackage;

    private String version;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("精品课程网站")
                .description("精品课程网站 API 接口稳定")
                .version(version)
                .termsOfServiceUrl("http://jpkc.test.cn")
                .license("jpkc")
                .licenseUrl("http://jpkc.test.cn")
                .build();
    }
}

