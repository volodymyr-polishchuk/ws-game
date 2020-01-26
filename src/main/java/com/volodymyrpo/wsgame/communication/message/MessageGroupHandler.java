package com.volodymyrpo.wsgame.communication.message;

import com.volodymyrpo.wsgame.state.GameState;

public interface MessageGroupHandler {

    void handleMessage(Message message, GameState gameState);
}
