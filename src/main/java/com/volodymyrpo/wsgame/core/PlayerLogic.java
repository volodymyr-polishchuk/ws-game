package com.volodymyrpo.wsgame.core;

import com.volodymyrpo.wsgame.entity.Player;
import com.volodymyrpo.wsgame.entity.Point;
import com.volodymyrpo.wsgame.math.Mathf;
import com.volodymyrpo.wsgame.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlayerLogic {

    Logger logger = LoggerFactory.getLogger(PlayerLogic.class);

    public void update(long time, GameState gameState) {
        gameState.getPlayers().forEach(player -> {
            if (Objects.nonNull(player.getTarget()) && !player.getPosition().equals(player.getTarget())) {
                Point newPosition = Mathf.moveToward(player.getPosition(), player.getTarget(), player.getSpeed());
                player.setPosition(newPosition);
//                logger.info("Player " + player.getNickname() + " moved to " + player.getPosition());
            }
        });
    }

    public boolean playerInGame(GameState gameState, String nickname) {
        return gameState.getPlayers().stream().anyMatch(player -> player.getNickname().equals(nickname));
    }


    public Player findPlayerByNickname(String nickname, GameState gameState) {
        return gameState.getPlayers()
                .stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);
    }
}
