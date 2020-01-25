package com.volodymyrpo.wsgame.configuration;

import com.volodymyrpo.wsgame.comunitation.websocket.PlayerWebSocketHandler;
import com.volodymyrpo.wsgame.engine.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private Logger logger = LoggerFactory.getLogger(PlayerWebSocketHandler.class);

    private final Game game;

    public WebSocketConfig(Game game) {
        this.game = game;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "player").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new PlayerWebSocketHandler(game);
    }

}
