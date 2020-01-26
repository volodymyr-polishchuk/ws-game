package com.volodymyrpo.wsgame.communication.message;

public abstract class PlayerMessage<T> implements Message<T> {

    @Override
    public MessageGroup getMessageGroup() {
        return MessageGroup.PLAYER;
    }
}
