package com.volodymyrpo.wsgame.math;

import com.volodymyrpo.wsgame.entity.Point;

public class Mathf {

    public static Point moveToward(Point position, Point target, double speed) {
        if (Math.abs(position.getX() - target.getX()) <= speed
                && Math.abs(position.getY() - target.getY()) <= speed) {
            return target;
        }

        Point newPoint = new Point(0, 0);

        if (Math.abs(position.getX() - target.getX()) <= speed) {
            newPoint.setX(target.getX());
        } else {
            double signX = Math.copySign(1, target.getX() - position.getX());
            newPoint.setX(position.getX() + signX * speed);
        }

        if (Math.abs(position.getY() - target.getY()) <= speed) {
            newPoint.setY(target.getY());
        } else {
            double signY = Math.copySign(1, target.getY() - position.getY());
            newPoint.setY(position.getY() + signY * speed);
        }

        return newPoint;
    }
}
