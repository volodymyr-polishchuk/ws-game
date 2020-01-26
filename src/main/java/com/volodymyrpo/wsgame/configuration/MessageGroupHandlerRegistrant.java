package com.volodymyrpo.wsgame.configuration;

import com.volodymyrpo.wsgame.communication.message.MessageGroup;
import com.volodymyrpo.wsgame.core.Core;

public class MessageGroupHandlerRegistrant {

    public static void registerAll(Core core) {
        core.getMessageHandlerHolder()
                .registerMessageHandlerGroup(MessageGroup.PLAYER, new PlayerMessageGroupHandler());
    }

}

