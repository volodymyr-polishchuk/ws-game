package com.volodymyrpo.wsgame.engine;

import com.volodymyrpo.wsgame.comunitation.websocket.PlayerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Configuration
public class Game {

    private Logger logger = LoggerFactory.getLogger(Game.class);

    private Map<PlayerSession, List<Consumer<String>>> players = new ConcurrentHashMap<>();

    @Bean
    public Game getInstance() {
        return new Game();
    }

    public void addPlayer(String name, WebSocketSession session) throws IOException {
        Optional<PlayerSession> playerSession = findPlayerSessionByName(name);
        if (playerSession.isPresent()) {
            if (playerSession.get().getSession().isOpen()) {
                session.sendMessage(new TextMessage("Error: Player already connected"));
                session.close(CloseStatus.NOT_ACCEPTABLE);
            } else {
                players.put(new PlayerSession(name, session), players.get(playerSession.get()));
            }
        } else {
            players.put(new PlayerSession(name, session), new CopyOnWriteArrayList<>());
        }

        boolean result = addPlayerMessageHandler(name, System.out::println);
        logger.info("Adding result %s", result);
    }

    public void handleMessageToPlayer(String name, String message) {
        Optional<PlayerSession> playerSession = findPlayerSessionByName(name);
        if (playerSession.isPresent()) {
            players.get(playerSession.get()).forEach(consumer -> consumer.accept(message));
        } else {
            logger.error("Message to player without PlayerSession. Name: %s; Message: %s", name, message);
        }
    }

    public boolean addPlayerMessageHandler(String name, Consumer<String> consumer) {
        Optional<PlayerSession> playerSession = findPlayerSessionByName(name);
        if (playerSession.isPresent()) {
            players.get(playerSession.get()).add(consumer);
            return true;
        } else {
            return false;
        }
    }

    private Optional<PlayerSession> findPlayerSessionByName(String name) {
        return players.keySet()
                .stream()
                .filter(playerSession -> Objects.equals(playerSession.getName(), name))
                .findFirst();
    }
}
