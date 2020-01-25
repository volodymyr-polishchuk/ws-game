package com.volodymyrpo.wsgame.communication;

import com.volodymyrpo.wsgame.state.GameState;

public interface MessageGroupHandler {

    void handleMessage(Message message, GameState gameState);
}
