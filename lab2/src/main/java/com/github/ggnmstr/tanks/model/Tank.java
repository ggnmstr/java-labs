package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.TankObject;
import com.github.ggnmstr.tanks.util.Direction;


public class Tank extends GamePrimitive {

    private final int speed;
    
    private final int bulletSize; 

    private Direction lastMove;

    private boolean shootable = true;

    private int hp;

    public Tank(int xPos, int yPos, int hp, int w, int h, int speed, int bulletSize) {
        this.width = w;
        this.height = h;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
        this.hp = hp;
        this.speed = speed;
        this.bulletSize = bulletSize;
    }

    public static Tank from(TankObject tankObject, int hp, int tankSpeed, int bulletSize) {
        return new Tank(tankObject.x(), tankObject.y(), hp,
                tankObject.width(), tankObject.height(), tankSpeed, bulletSize);
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void move(Direction direction, boolean changeDir) {
        switch (direction) {
            case UP -> this.yPos -= speed;
            case DOWN -> this.yPos += speed;
            case RIGHT -> this.xPos += speed;
            case LEFT -> this.xPos -= speed;
        }
        if (changeDir) this.lastMove = direction;

    }

    public boolean takeDamage() {
        this.hp--;
        return hp > 0;
    }

    public void makeShootable() {
        this.shootable = true;
    }

    public int getHP() {
        return hp;
    }

    public int getBulletSize() {
        return bulletSize;
    }

    public Bullet shoot() {
        if (!shootable) return null;
        int startX = 0, startY = 0;
        switch (lastMove) {
            case UP -> {
                startX = xPos + width / 2 - bulletSize / 2;
                startY = yPos - bulletSize;
            }
            case DOWN -> {
                startX = xPos + width / 2 - bulletSize / 2;
                startY = yPos + height;
            }
            case RIGHT -> {
                startX = xPos + width;
                startY = yPos + height / 2 - bulletSize / 2;
            }
            case LEFT -> {
                startX = xPos - bulletSize;
                startY = yPos + height / 2 - bulletSize / 2;
            }
        }
        Bullet bullet = new Bullet(startX, startY, lastMove, this);
        this.shootable = false;
        return bullet;
    }
}

