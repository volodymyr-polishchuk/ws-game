package com.volodymyrpo.wsgame.communication;

import com.volodymyrpo.wsgame.core.Core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandlerHolder {
    
    private final Map<MessageGroup, MessageGroupHandler> messageGroupHandlers;
    private final Core core;

    public MessageHandlerHolder(Core core) {
        this.core = core;
        messageGroupHandlers = new ConcurrentHashMap<>();
    }

    public void registerMessageHandlerGroup(MessageGroup messageGroup, MessageGroupHandler groupHandler) {
        this.messageGroupHandlers.put(messageGroup, groupHandler);
    }

    public void handleMessage(Message message) {
        messageGroupHandlers
                .get(message.getMessageGroup())
                .handleMessage(message, core.getGameState());
    }
}
