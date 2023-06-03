package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.TankObject;
import com.github.ggnmstr.tanks.util.Direction;


public class Tank extends GamePrimitive {

    public static final int TANKSIZE = 60;
    private final int TANKSPEED = 5;

    private Direction lastMove;

    private boolean shootable = true;

    private int hp;

    public Tank(int xPos, int yPos, int hp) {
        this.width = TANKSIZE;
        this.height = TANKSIZE;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
        this.hp = hp;
    }

    public static Tank from(TankObject tankObject, int hp) {
        return new Tank(tankObject.x(), tankObject.y(), hp);
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void move(Direction direction, boolean changeDir) {
        switch (direction){
            case UP -> this.yPos -= TANKSPEED;
            case DOWN -> this.yPos += TANKSPEED;
            case RIGHT -> this.xPos += TANKSPEED;
            case LEFT -> this.xPos -= TANKSPEED;
        }
        if (changeDir) this.lastMove = direction;

    }

    public boolean takeDamage(){
        this.hp--;
        return hp > 0;
    }

    public void makeShootable(){
        this.shootable = true;
    }

    public int getHP(){
        return hp;
    }

    public Bullet shoot() {
        if (!shootable) return null;
        int startX = 0, startY = 0;
        switch (lastMove) {
            case UP -> {
                startX = xPos + width / 2 - Bullet.BULLETSHORT/2;
                startY = yPos - Bullet.BULLETLONG;
            }
            case DOWN -> {
                startX = xPos + width / 2 - Bullet.BULLETSHORT/2;
                startY = yPos + height;
            }
            case RIGHT -> {
                startX = xPos + width;
                startY = yPos + height / 2 - Bullet.BULLETSHORT/2;
            }
            case LEFT -> {
                startX = xPos - Bullet.BULLETLONG;
                startY = yPos + height / 2 - Bullet.BULLETSHORT/2;
            }
        }
        Bullet bullet = new Bullet(startX, startY, lastMove,this);
        this.shootable = false;
        return bullet;
    }
}

