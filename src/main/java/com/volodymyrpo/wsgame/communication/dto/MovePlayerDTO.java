package com.volodymyrpo.wsgame.communication.dto;

import com.volodymyrpo.wsgame.entity.Point;
import lombok.Data;

@Data
public class MovePlayerDTO {

    public MovePlayerDTO() {
    }

    public MovePlayerDTO(String playerName, Point target) {
        this.playerName = playerName;
        this.target = target;
    }

    private String playerName;
    private Point target;

}
