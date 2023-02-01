package com.example.springwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 用户可以订阅以这些前缀为开头的主体，并且只能订阅这些主题
         */
        registry.enableSimpleBroker("/user","/message","/topic");
        /**
         * 通过客户端的消息需要带上app前缀，通过Broker进行转发给响应的controller
         */
//        registry.setApplicationDestinationPrefixes("app");
    }
}
