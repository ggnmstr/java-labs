package com.github.ggnmstr.tanks.model;

//
//record Position(int x, int y) {}
//record Dimension(int width, int height) {}
//
//class BaseClass {
//
//    private final Position position;
//    private final Dimension dimension;
//
//    BaseClass(Position position, Dimension dimension) {
//        this.position = position;
//        this.dimension = dimension;
//    }
//
//    public Position position() {
//        return position;
//    }
//
//    public Dimension dimension() {
//        return dimension;
//    }
//}

public abstract class GamePrimitive {

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
