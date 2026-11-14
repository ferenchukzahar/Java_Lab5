package com.lab5.task2;

import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    private static final long serialVersionUID = 1L;
    private String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public String getShapeColor() {
        return shapeColor;
    }

    public abstract double calcArea();

    @Override
    public String toString() {
        return String.format("Колір: %-10s | Площа: %8.2f", shapeColor, calcArea());
    }
}
