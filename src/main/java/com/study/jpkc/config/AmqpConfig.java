package com.study.jpkc.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 22:12
 * @desc 消息中间件配置
 */
@Configuration
public class AmqpConfig {

    /**
     * 序列化规则
     * @return jackson
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
