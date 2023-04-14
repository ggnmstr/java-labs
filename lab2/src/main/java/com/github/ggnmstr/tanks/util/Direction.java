package com.github.ggnmstr.tanks.util;

public enum Direction {
    DOWN,
    LEFT,
    RIGHT,
    UP,
    NONE;

    public static Direction getOpposite(Direction direction){
        return switch (direction){
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case NONE -> NONE;
        };
    }
}
