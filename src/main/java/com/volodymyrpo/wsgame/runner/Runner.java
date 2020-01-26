package com.volodymyrpo.wsgame.runner;

import com.volodymyrpo.wsgame.configuration.MessageGroupHandlerRegistrant;
import com.volodymyrpo.wsgame.core.Core;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("runner")
public class Runner {

    private final Core core;

    public Runner(Core core) {
        this.core = core;
    }

    @PostConstruct
    public void initialize() {
        MessageGroupHandlerRegistrant.registerAll(core);
        core.run();
    }
}
