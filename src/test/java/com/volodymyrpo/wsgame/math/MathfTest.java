package com.volodymyrpo.wsgame.math;

import com.volodymyrpo.wsgame.entity.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathfTest {

    @Test
    public void test__1() {
        Point current = new Point(0,0);
        Point target = new Point(5, 5);
        double speed = 0.5;

        Point newPosition = Mathf.moveToward(current, target, speed);

        assertEquals(0.5, newPosition.getX());
        assertEquals(0.5, newPosition.getY());
    }

    @Test
    public void test__2() {
        Point current = new Point(0,0);
        Point target = new Point(5, 5);
        double speed = 2;

        Point newPosition = Mathf.moveToward(current, target, speed);
        newPosition = Mathf.moveToward(newPosition, target, speed);
        newPosition = Mathf.moveToward(newPosition, target, speed);
        newPosition = Mathf.moveToward(newPosition, target, speed);

        assertEquals(5, newPosition.getX());
        assertEquals(5, newPosition.getY());
    }
}
