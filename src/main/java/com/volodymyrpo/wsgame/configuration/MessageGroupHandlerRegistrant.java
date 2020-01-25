package com.volodymyrpo.wsgame.configuration;

import com.volodymyrpo.wsgame.communication.Message;
import com.volodymyrpo.wsgame.communication.MessageGroup;
import com.volodymyrpo.wsgame.communication.MessageGroupHandler;
import com.volodymyrpo.wsgame.core.Core;
import com.volodymyrpo.wsgame.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageGroupHandlerRegistrant {

    public static void registerAll(Core core) {
        core.getMessageHandlerHolder()
                .registerMessageHandlerGroup(MessageGroup.PLAYER, new PlayerMessageGroupHandler());
    }

}

class PlayerMessageGroupHandler implements MessageGroupHandler {

    private final Logger logger = LoggerFactory.getLogger(PlayerMessageGroupHandler.class);

    @Override
    public void handleMessage(Message message, GameState gameState) {
        switch (message.getMessageAction()) {
            case MOVE:
                logger.info("Handled MOVE");
                break;

            case ATTACK:
                logger.info("Handled ATTACK");
                break;
        }
    }
}
