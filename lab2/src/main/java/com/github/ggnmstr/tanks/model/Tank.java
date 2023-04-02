package com.github.ggnmstr.tanks.model;

public class Tank extends GameObject {
    private int bullets;
    private int health;

    private Direction lastMove;

    public Tank(int xPos, int yPos) {
        this.width = GameParameters.TANKSIZE;
        this.height = GameParameters.TANKSIZE;
        this.health = 100;
        this.bullets = 10;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void move(int xDelta, int yDelta) {
        this.xPos += xDelta;
        this.yPos += yDelta;
        for (GameObject x : GameManager.objList) {
            if (x != this && GameManager.isCollision(x, this)) {
                this.xPos -= xDelta;
                this.yPos -= yDelta;
            }
        }
        if (xDelta > 0) lastMove = Direction.RIGHT;
        if (xDelta < 0) lastMove = Direction.LEFT;
        if (yDelta < 0) lastMove = Direction.UP;
        if (yDelta > 0) lastMove = Direction.DOWN;
    }

    void shoot() {
        if (bullets == 0) return;
        int startX = 0, startY = 0;
        switch (lastMove) {
            case UP -> {
                startX = xPos + width / 2;
                // -40 - height of bullet
                startY = yPos - GameParameters.BULLETLONG;
            }
            case DOWN -> {
                startX = xPos + width / 2;
                startY = yPos + height;
            }
            case RIGHT -> {
                startX = xPos + width;
                startY = yPos + height / 2;
            }
            case LEFT -> {
                startX = xPos - GameParameters.BULLETLONG;
                startY = yPos + height / 2;
            }
        }
        Bullet bullet = new Bullet(startX, startY, lastMove);
        GameManager.objList.add(bullet);
        //bullets--;
    }
}

