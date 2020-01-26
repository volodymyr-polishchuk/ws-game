package com.volodymyrpo.wsgame.core;

import com.volodymyrpo.wsgame.communication.message.MessageHandlerHolder;
import com.volodymyrpo.wsgame.state.GameState;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class Core {

    private final long TICK_DELAY = 1000;

    private final PlayerLogic playerLogic;

    @Getter
    private final MessageHandlerHolder messageHandlerHolder;

    @Getter
    private final GameState gameState;

    private Timer timer;

    public Core(PlayerLogic playerLogic) {
        this.playerLogic = playerLogic;
        this.messageHandlerHolder = new MessageHandlerHolder(this);
        this.gameState = new GameState();
        this.timer = new Timer();
    }

    public void run() {
        timer.scheduleAtFixedRate(getTickTask(), 0, TICK_DELAY);
    }

    private TimerTask getTickTask() {
        return new TimerTask() {

            Logger logger = LoggerFactory.getLogger(TimerTask.class);

            public void run() {
                playerLogic.update(0L, gameState);
                logger.info("New tick update");
            }
        };
    }

}
