package com.study.jpkc.config;

import com.study.jpkc.interceptor.ChannelInterceptor;
import com.study.jpkc.interceptor.HandShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @Author Harlan
 * @Date 2021/2/12
 * @Desc WebSocket长连接通信
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Bean
    public ChannelInterceptor channelInterceptor() {
        return new ChannelInterceptor();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //添加访问域名限制可以防止跨域socket连接
        //setAllowedOrigins("http://localhost:8080")
        registry.addEndpoint("/live")
                .setAllowedOrigins("*")
                .addInterceptors(new HandShakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*.enableSimpleBroker("/topic","/queue");*/
        //假如需要第三方消息代理，比如RabbitMQ在这里配置
        registry.setApplicationDestinationPrefixes("/live")
                .enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("47.108.151.199")
                .setRelayPort(5672)
                .setClientLogin("mqAdmin")
                .setClientPasscode("lb82ndLF-mq")
                .setSystemLogin("mqAdmin")
                .setSystemPasscode("lb82ndLF-mq")
                .setSystemHeartbeatReceiveInterval(5000)
                .setSystemHeartbeatSendInterval(5000);
    }
}
