package com.study.jpkc.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Harlan
 * @Date 2021/2/7
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.mail")
public class MailProperties {

    private String region;

    private String accessKey;

    private String accessSecret;
}
