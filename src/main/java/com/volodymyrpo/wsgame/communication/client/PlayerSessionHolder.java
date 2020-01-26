package com.volodymyrpo.wsgame.communication.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volodymyrpo.wsgame.state.GameState;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerSessionHolder {

    private ObjectMapper mapper = new ObjectMapper();
    private String lastValue;

    private Map<String, WebSocketSession> map;

    public PlayerSessionHolder() {
        this.map = new ConcurrentHashMap<>();
    }

    public void joinPlayer(String nickname, WebSocketSession session) {
        map.put(nickname, session);
    }

    public void kickPlayer(String nickname) {
        map.remove(nickname);
    }

    public void sendState(GameState gameState) {
        map.forEach((nickname, session) -> {
            try {
                String value = mapper.writeValueAsString(gameState);
                if (session.isOpen() && !value.equals(lastValue)) {
                    session.sendMessage(new TextMessage(value));
                    lastValue = value;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
