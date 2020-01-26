package com.volodymyrpo.wsgame.communication.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volodymyrpo.wsgame.communication.dto.MovePlayerDTO;
import com.volodymyrpo.wsgame.communication.message.Message;
import com.volodymyrpo.wsgame.communication.message.MessageAction;
import com.volodymyrpo.wsgame.communication.message.MessageGroup;
import com.volodymyrpo.wsgame.communication.message.PlayerMessage;
import com.volodymyrpo.wsgame.core.Core;
import com.volodymyrpo.wsgame.entity.Player;
import com.volodymyrpo.wsgame.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Map;

public class PlayerWebSocketHandler extends PlayerWebSocketHandlerSkeleton {

    private final Logger logger = LoggerFactory.getLogger(PlayerWebSocketHandler.class);

    private final Core core;

    public PlayerWebSocketHandler(Core core) {
        this.core = core;
    }

    @Override
    public void afterConnectionEstablished(String nickname) {
        core.getMessageHandlerHolder().handleMessage(new PlayerMessage<Player>() {

            @Override
            public MessageAction getMessageAction() {
                return MessageAction.JOIN;
            }

            @Override
            public Player getContent() {
                return new Player(nickname, Point.ZERO);
            }
        });
    }

    @Override
    public void afterConnectionClosed(String nickname, CloseStatus closeStatus) throws Exception {
        core.getMessageHandlerHolder().handleMessage(new PlayerMessage() {
            @Override
            public MessageAction getMessageAction() {
                return MessageAction.LEAVE;
            }

            @Override
            public Object getContent() {
                return null;
            }
        });
    }


    @Override
    public void handleMessage(String playerNickname, WebSocketMessage socketMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageTemplate template = objectMapper.readValue((String) socketMessage.getPayload(), MessageTemplate.class);
        Message message = parseMessage(playerNickname, template);
        core.getMessageHandlerHolder().handleMessage(message);
    }

    private Message parseMessage(String playerNickname, MessageTemplate template) {
        if (template.getGroup() == MessageGroup.PLAYER) {
            switch (template.getAction()) {
                case MOVE: {
                    return parseMoveMessage(playerNickname, template.getContent());
                }
                case ATTACK: {
                    return null;
                }
            }
        }
        return null;
    }

    private Message parseMoveMessage(String playerNickname, Map<String, Object> content) {
        return new PlayerMessage<MovePlayerDTO>() {
            @Override
            public MessageAction getMessageAction() {
                return MessageAction.MOVE;
            }

            @Override
            public MovePlayerDTO getContent() {
                return new MovePlayerDTO(
                        playerNickname,
                        new Point(
                                (int) content.get("x"),
                                (int) content.get("y")
                        )
                );
            }
        };
    }


}
