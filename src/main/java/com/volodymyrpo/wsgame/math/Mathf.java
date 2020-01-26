package com.volodymyrpo.wsgame.math;

import com.volodymyrpo.wsgame.entity.Point;

public class Mathf {

    public static Point moveToward(Point position, Point target, double speed) {
        if (Math.abs(position.getX() - target.getX()) <= speed
                && Math.abs(position.getY() - target.getY()) <= speed) {
            return target;
        }

        double signX = Math.copySign(1, target.getX() - position.getX());
        double signY = Math.copySign(1, target.getY() - position.getY());

        return new Point(
                position.getX() + signX * speed,
                position.getY() + signY * speed
        );
    }
}
