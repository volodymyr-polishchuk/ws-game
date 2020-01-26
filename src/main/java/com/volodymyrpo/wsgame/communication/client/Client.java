package com.volodymyrpo.wsgame.communication.client;

import com.volodymyrpo.wsgame.communication.dto.MovePlayerDTO;
import com.volodymyrpo.wsgame.communication.message.Message;
import com.volodymyrpo.wsgame.communication.message.MessageAction;
import com.volodymyrpo.wsgame.communication.message.MessageGroup;
import com.volodymyrpo.wsgame.core.Core;
import com.volodymyrpo.wsgame.entity.Player;
import com.volodymyrpo.wsgame.entity.Point;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@DependsOn("runner")
public class Client {

    private final Core core;

    public Client(Core core) {
        this.core = core;
    }

    @PostConstruct
    public void init() {
//        this.core.getMessageHandlerHolder().handleMessage(new Message<Player>() {
//            @Override
//            public MessageGroup getMessageGroup() {
//                return MessageGroup.PLAYER;
//            }
//
//            @Override
//            public MessageAction getMessageAction() {
//                return MessageAction.JOIN;
//            }
//
//            @Override
//            public Player getContent() {
//                return new Player("FirstPlayer1", Point.ZERO);
//            }
//        });
//
//        this.core.getMessageHandlerHolder().handleMessage(new Message<MovePlayerDTO>() {
//            @Override
//            public MessageGroup getMessageGroup() {
//                return MessageGroup.PLAYER;
//            }
//
//            @Override
//            public MessageAction getMessageAction() {
//                return MessageAction.MOVE;
//            }
//
//            @Override
//            public MovePlayerDTO getContent() {
//                MovePlayerDTO dto = new MovePlayerDTO();
//                dto.setPlayerName("FirstPlayer1");
//                dto.setTarget(new Point(5, 5));
//                return dto;
//            }
//        });


    }
}
