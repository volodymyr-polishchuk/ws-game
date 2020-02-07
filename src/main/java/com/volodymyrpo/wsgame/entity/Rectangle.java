package com.volodymyrpo.wsgame.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Rectangle {

    private double x;
    private double y;
    private double width;
    private double height;

    public Point getCenter() {
        return new Point(x + width / 2, y + height / 2);
    }

    public void setCenter(Point center) {
        this.x = center.getX() - width / 2;
        this.y = center.getY() - height / 2;
    }

    public double getRight() {
        return x + width;
    }

    public double getBottom() {
        return y + height;
    }

    @JsonIgnore
    public boolean contains(Point point) {
        return this.x <= point.getX()
                && this.getRight() >= point.getX()
                && this.y <= point.getY()
                && this.getBottom() >= point.getY();
    }
}
