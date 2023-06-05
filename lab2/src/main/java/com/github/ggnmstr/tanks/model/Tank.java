package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.TankObject;
import com.github.ggnmstr.tanks.util.Direction;


public class Tank extends GamePrimitive {

    private final int speed;

    private Direction lastMove;

    private boolean shootable = true;

    private int hp;

    public Tank(int xPos, int yPos, int hp, int w, int h, int speed) {
        this.width = w;
        this.height = h;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
        this.hp = hp;
        this.speed = speed;
    }

    public static Tank from(TankObject tankObject, int hp, int tankSpeed) {
        return new Tank(tankObject.x(), tankObject.y(), hp,
                tankObject.width(), tankObject.height(), tankSpeed);
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

    public Bullet shoot() {
        if (!shootable) return null;
        int startX = 0, startY = 0;
        switch (lastMove) {
            case UP -> {
                startX = xPos + width / 2 - Bullet.BULLETSIZE / 2;
                startY = yPos - Bullet.BULLETSIZE;
            }
            case DOWN -> {
                startX = xPos + width / 2 - Bullet.BULLETSIZE / 2;
                startY = yPos + height;
            }
            case RIGHT -> {
                startX = xPos + width;
                startY = yPos + height / 2 - Bullet.BULLETSIZE / 2;
            }
            case LEFT -> {
                startX = xPos - Bullet.BULLETSIZE;
                startY = yPos + height / 2 - Bullet.BULLETSIZE / 2;
            }
        }
        Bullet bullet = new Bullet(startX, startY, lastMove, this);
        this.shootable = false;
        return bullet;
    }
}

