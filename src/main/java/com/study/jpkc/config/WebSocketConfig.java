package com.study.jpkc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author Harlan
 * @Date 2021/2/12
 * @Desc WebSocket长连接通信
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/live")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.setApplicationDestinationPrefixes("/demo")
                .enableStompBrokerRelay("/topic","/queue")
                .setRelayHost("47.108.151.199")
                .setRelayPort(5672)
                .setClientLogin("mqAdmin")
                .setClientPasscode("lb82ndLF-mq")
                .setSystemLogin("mqAdmin")
                .setSystemPasscode("lb82ndLF-mq")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(5000);

    }


}
