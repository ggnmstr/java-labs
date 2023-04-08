package com.github.ggnmstr.tanks.model;

public enum Direction {
    UP,
    LEFT,
    DOWN,
    RIGHT;
    public static Direction getOpposite(Direction direction){
        switch (direction){
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
        }
        return DOWN;
    }
}
