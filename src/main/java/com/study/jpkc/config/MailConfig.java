package com.study.jpkc.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.study.jpkc.properties.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Harlan
 * @Date 2021/2/7
 */
@Configuration
public class MailConfig {

    @Bean
    public IClientProfile iClientProfile(MailProperties mailProperties) {
        return DefaultProfile.getProfile(mailProperties.getRegion(),
                mailProperties.getAccessKey(), mailProperties.getAccessSecret());
    }

    @Bean
    public IAcsClient iAcsClient(IClientProfile iClientProfile) {
        return new DefaultAcsClient(iClientProfile);
    }
}
