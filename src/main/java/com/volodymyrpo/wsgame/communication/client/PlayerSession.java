package com.volodymyrpo.wsgame.communication.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@EqualsAndHashCode
public class PlayerSession {

    private String name;
    private WebSocketSession session;

    public PlayerSession(String name, WebSocketSession session) {
        this.name = name;
        this.session = session;
    }

}
