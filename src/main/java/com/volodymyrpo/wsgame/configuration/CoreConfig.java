package com.volodymyrpo.wsgame.configuration;

import com.volodymyrpo.wsgame.core.Core;
import com.volodymyrpo.wsgame.core.PlayerLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CoreConfig {

    private final Core core;

    public CoreConfig() {
        this.core = getCore();
    }

    @PostConstruct
    public void initialize() {
        MessageGroupHandlerRegistrant.registerAll(core);
        core.run();
    }

    @Bean
    public PlayerLogic getPlayerLogin() {
        return new PlayerLogic();
    }

    @Bean
    public Core getCore() {
        return new Core(getPlayerLogin());
    }
}
