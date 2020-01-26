package com.volodymyrpo.wsgame.communication.client;

import com.volodymyrpo.wsgame.communication.message.MessageAction;
import com.volodymyrpo.wsgame.communication.message.MessageGroup;
import lombok.Data;

import java.util.Map;

@Data
public class MessageTemplate {

    private MessageGroup group;
    private MessageAction action;
    private Map<String, Object> content;
}
