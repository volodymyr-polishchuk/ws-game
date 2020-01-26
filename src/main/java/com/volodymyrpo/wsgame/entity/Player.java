package com.volodymyrpo.wsgame.entity;

import lombok.Data;

@Data
public class Player {

    private String nickname;
    private Point position;
    private Point target;
    private double speed = 0.6;
    private double health = 10;
    private int maxHealth = 100;
    private int healingPerSecond = 5;
    private long lastHealTime = 0;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public Player(String nickname, Point position) {
        this.nickname = nickname;
        this.position = position.copy();
    }

    public void moveTo(Point point) {
        this.position = point;
        this.target = point;
    }
}
