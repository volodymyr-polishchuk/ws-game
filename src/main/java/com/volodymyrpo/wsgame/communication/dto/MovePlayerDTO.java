package com.volodymyrpo.wsgame.communication.dto;

import com.volodymyrpo.wsgame.entity.Player;
import com.volodymyrpo.wsgame.entity.Point;
import lombok.Data;

@Data
public class MovePlayerDTO {

    private Player player;
    private Point target;

}
