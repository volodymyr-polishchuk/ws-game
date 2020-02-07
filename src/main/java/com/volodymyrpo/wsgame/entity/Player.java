package com.volodymyrpo.wsgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Player {

    @JsonIgnore
    private static final int WIDTH = 72;
    @JsonIgnore
    private static final int HEIGHT = 72;

    private String nickname;
    private Point position;
    private Point target;
    private double speed = 40;
    private double health = 10;
    private int maxHealth = 100;
    private int healingPerSecond = 5;
    @JsonIgnore
    private long lastHealTime = 0;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public Player(String nickname, Point position) {
        this.nickname = nickname;
        this.position = position.copy();
    }

    @JsonIgnore
    public void moveTo(Point point) {
        this.position = point;
        this.target = point;
    }

    public Rectangle getBounce() {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(HEIGHT);
        rectangle.setWidth(WIDTH);
        rectangle.setCenter(position);
        return rectangle;
    }
}
