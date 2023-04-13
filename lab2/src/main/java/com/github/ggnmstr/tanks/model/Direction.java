package com.github.ggnmstr.tanks.model;

// CR: move to utils package
public enum Direction {
    DOWN,
    LEFT,
    RIGHT,
    UP;

    // CR: Direction.NONE
    public static Direction getOpposite(Direction direction){
        return switch (direction){
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
