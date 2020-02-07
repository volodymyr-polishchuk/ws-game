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

    public void update(long diff, GameState gameState) {
        gameState.getPlayers().forEach(player -> {
            move(player, diff);
            checkHealth(player);
            heal(player, diff);
        });
    }

    private void checkHealth(Player player) {
        if (player.getHealth() <= 0) {
            kill(player);
        }
    }

    private void kill(Player player) {
        player.setHealth(player.getMaxHealth());
        player.moveTo(new Point(100, 100));
    }

    private void heal(Player player, long diff) {
        if (player.getHealth() < player.getMaxHealth()
            && System.currentTimeMillis() - player.getLastHealTime() > 2000) {
            double healPoints = player.getHealingPerSecond() * 2;
            if (player.getHealth() + healPoints > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            } else {
                player.setHealth(player.getHealth() + healPoints);
            }
            player.setLastHealTime(System.currentTimeMillis());
        }
    }

    private void move(Player player, long diff) {
        if (Objects.nonNull(player.getTarget()) && !player.getPosition().equals(player.getTarget())) {
            double speedPerFrame = player.getSpeed() * (diff / 1000d);
            Point newPosition = Mathf.moveToward(player.getPosition(), player.getTarget(), speedPerFrame);
            player.setPosition(newPosition);
        }
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
