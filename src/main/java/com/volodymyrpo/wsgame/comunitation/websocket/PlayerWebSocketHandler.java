package com.volodymyrpo.wsgame.comunitation.websocket;

import com.volodymyrpo.wsgame.engine.Game;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class PlayerWebSocketHandler extends WebSocketHandlerSkeleton {

    private final Game game;

    public PlayerWebSocketHandler(Game game) {
        this.game = game;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Optional<String> name = getNameFromQuery(session);
        if (name.isPresent()) {
            game.addPlayer(name.get(), session);
        } else {
            session.sendMessage(new TextMessage("Error: Player name not provided"));
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Optional<String> name = getNameFromQuery(session);
        if (name.isPresent()) {
            game.handleMessageToPlayer(name.get(), (String) message.getPayload());
        } else {
            session.sendMessage(new TextMessage("Error: Player name not provided"));
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    private Optional<String> getNameFromQuery(WebSocketSession session) {
        URI uri = session.getUri();
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
