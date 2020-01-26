package com.volodymyrpo.wsgame.communication.client;

import com.volodymyrpo.wsgame.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private Logger logger = LoggerFactory.getLogger(PlayerWebSocketHandler.class);

    private final Core core;

    public WebSocketConfig(Core core) {
        this.core = core;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "player")
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        Optional<String> nameFromQuery = PlayerWebSocketHandler.getNameFromQuery(request.getURI());
                        return nameFromQuery.isPresent()
                                && !core.getPlayerLogic().playerInGame(core.getGameState(), nameFromQuery.get());
                    }
                })
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new PlayerWebSocketHandler(core);
    }

}
