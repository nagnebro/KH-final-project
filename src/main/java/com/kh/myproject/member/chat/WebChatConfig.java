package com.kh.myproject.member.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@ServerEndpoint("/test")

@Configuration
@EnableWebSocket
@Service
public class WebChatConfig implements WebSocketConfigurer {

    @Autowired
    MyHandler myHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(myHandler,"/ws/{roomId}/{userNumber}");
    }


}
