package com.study.jpkc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 15:10
 * @desc swagger 配置
 */
@Configuration
@ConfigurationProperties(prefix = "swagger")
@EnableSwagger2
@Data
public class SwaggerConfig {

    private Boolean swaggerEnabled;

    private String basePackage;

    private String version;

    private String tittle;

    private String description;

    private String termsOfServiceUrl;

    private String license;

    private String licenseUrl;


    @Bean
    public Docket docket() {
        //全局参数
        Parameter token = new ParameterBuilder().name("Authorization")
                .description("用户登录Token")
                .parameterType("header")
                .modelRef(new ModelRef("String"))
                .required(true)
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(token);
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket docketState() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .groupName("获取Token")
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.ant("/getToken"))
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(tittle)
                .description(description)
                .version(version)
                .termsOfServiceUrl(termsOfServiceUrl)
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }
}

