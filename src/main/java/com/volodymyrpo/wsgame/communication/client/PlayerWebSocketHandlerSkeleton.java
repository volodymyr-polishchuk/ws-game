package com.volodymyrpo.wsgame.communication.client;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public abstract class PlayerWebSocketHandlerSkeleton implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.afterConnectionEstablished(getNameFromQuery(session.getUri()).get(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        this.handleMessage(getNameFromQuery(session.getUri()).orElseThrow(Exception::new), (WebSocketMessage<String>) message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception { }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        this.afterConnectionClosed(getNameFromQuery(session.getUri()).get(), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void handleMessage(String nickname, WebSocketMessage<String> message) throws Exception {

    }

    public void afterConnectionEstablished(String nickname, WebSocketSession session) {

    }

    public void afterConnectionClosed(String nickname, CloseStatus closeStatus) throws Exception {

    }

    public static Optional<String> getNameFromQuery(URI uri) {
        if (uri != null) {
            String query = uri.getQuery();
            String[] arguments = query.split("&");
            Optional<String[]> optionalName = Arrays.stream(arguments)
                    .map(s -> s.split("="))
                    .filter(strings -> strings.length == 2)
                    .filter(strings -> Objects.equals(strings[0], "name"))
                    .findFirst();
            if (optionalName.isPresent() && optionalName.get()[1].length() > 0) {
                return Optional.of(optionalName.get()[1]);
            }
        }
        return Optional.empty();
    }
}
