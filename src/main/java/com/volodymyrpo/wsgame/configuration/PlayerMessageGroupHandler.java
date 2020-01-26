package com.volodymyrpo.wsgame.configuration;

import com.volodymyrpo.wsgame.communication.dto.MovePlayerDTO;
import com.volodymyrpo.wsgame.communication.message.Message;
import com.volodymyrpo.wsgame.communication.message.MessageGroupHandler;
import com.volodymyrpo.wsgame.entity.Player;
import com.volodymyrpo.wsgame.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

class PlayerMessageGroupHandler implements MessageGroupHandler {

    private final Logger logger = LoggerFactory.getLogger(PlayerMessageGroupHandler.class);

    @Override
    public void handleMessage(Message message, GameState gameState) {
        switch (message.getMessageAction()) {
            case MOVE: {
                logger.info("Handled MOVE");
                MovePlayerDTO dto = (MovePlayerDTO) message.getContent();
                Player player = gameState.getPlayers()
                        .stream()
                        .filter(value -> Objects.equals(value.getNickname(), dto.getPlayerName()))
                        .findFirst()
                        .get();
                player.setTarget(dto.getTarget().copy());
                break;
            }

            case ATTACK: {
                logger.info("Handled ATTACK");
                break;
            }

            case JOIN: {
                Player player = (Player) message.getContent();
                gameState.getPlayers().add(player);
                logger.info("Handled JOIN");
                break;
            }

            case LEAVE: {
                String nickname = (String) message.getContent();
                gameState.getPlayers()
                        .removeIf(value -> value.getNickname().equals(nickname));
                logger.info("Handled LEAVE");
            }
        }
    }
}
