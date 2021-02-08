package com.study.jpkc.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Harlan
 * @Date 2021/2/8
 */
@ConfigurationProperties(prefix = "aliyun.sms")
@Component
@Data
public class AliSmsProperties {

    private String accessKeyId;

    private String accessSecret;

    private String regionId;

    private String signName;

    private String sysDomain;

    private String sysVersion;
}
