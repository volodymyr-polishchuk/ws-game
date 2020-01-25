package com.volodymyrpo.wsgame.communication;

public interface Message {

    MessageGroup getMessageGroup();

    MessageAction getMessageAction();

    String getContent();
}
