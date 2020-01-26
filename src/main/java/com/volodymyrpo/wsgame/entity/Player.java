package com.volodymyrpo.wsgame.entity;

import lombok.Data;

@Data
public class Player {

    private String nickname;
    private Point position;
    private Point target;
    private double speed = 0.6;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public Player(String nickname, Point position) {
        this.nickname = nickname;
        this.position = position.copy();
    }

}
