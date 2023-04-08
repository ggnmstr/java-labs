package com.github.ggnmstr.tanks.model;

import static com.github.ggnmstr.tanks.model.BattleField.isCollision;

public class Tank extends GameObject {

    private int bullets;
    private Direction lastMove;

    public Tank(int xPos, int yPos) {
        this.width = GameParameters.TANKSIZE;
        this.height = GameParameters.TANKSIZE;
        this.bullets = 10;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void move(int xDelta, int yDelta) {
        this.xPos += xDelta;
        this.yPos += yDelta;
        if (xDelta > 0) lastMove = Direction.RIGHT;
        if (xDelta < 0) lastMove = Direction.LEFT;
        if (yDelta < 0) lastMove = Direction.UP;
        if (yDelta > 0) lastMove = Direction.DOWN;
    }

    Bullet shoot() {
        if (bullets == 0) return null;
        int startX = 0, startY = 0;
        switch (lastMove) {
            case UP -> {
                startX = xPos + width / 2 - GameParameters.BULLETSHORT/2;
                startY = yPos - GameParameters.BULLETLONG;
            }
            case DOWN -> {
                startX = xPos + width / 2 - GameParameters.BULLETSHORT/2;
                startY = yPos + height;
            }
            case RIGHT -> {
                startX = xPos + width;
                startY = yPos + height / 2 - GameParameters.BULLETSHORT/2;
            }
            case LEFT -> {
                startX = xPos - GameParameters.BULLETLONG;
                startY = yPos + height / 2 - GameParameters.BULLETSHORT/2;
            }
        }
        Bullet bullet = new Bullet(startX, startY, lastMove);
        return bullet;
        //bullets--;
    }
}

