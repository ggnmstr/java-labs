package com.github.ggnmstr.tanks.model;

public abstract class GameObject {

    protected int xPos;
    protected int yPos;

    protected int width;
    protected int height;

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
