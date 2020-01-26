package com.volodymyrpo.wsgame.communication.message;

public interface Message<T> {

    MessageGroup getMessageGroup();

    MessageAction getMessageAction();

    T getContent();
}
