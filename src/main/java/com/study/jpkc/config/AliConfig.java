package com.study.jpkc.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.study.jpkc.common.properties.AliMailProperties;
import com.study.jpkc.common.properties.AliSmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Harlan
 * @Date 2021/2/7
 */
@Configuration
public class AliConfig {

    @Bean
    public IClientProfile mailClientProfile(AliMailProperties aliMailProperties) {
        return DefaultProfile.getProfile(aliMailProperties.getRegion(),
                aliMailProperties.getAccessKey(), aliMailProperties.getAccessSecret());
    }

    @Bean
    public IAcsClient mailClient(IClientProfile mailClientProfile) {
        return new DefaultAcsClient(mailClientProfile);
    }

    @Bean
    public IClientProfile smsClientProfile(AliSmsProperties aliSmsProperties) {
        return DefaultProfile.getProfile(aliSmsProperties.getRegionId(),
                aliSmsProperties.getAccessKeyId(), aliSmsProperties.getAccessSecret());
    }

    @Bean
    public IAcsClient smsClient(IClientProfile smsClientProfile) {
        return new DefaultAcsClient(smsClientProfile);
    }
}
