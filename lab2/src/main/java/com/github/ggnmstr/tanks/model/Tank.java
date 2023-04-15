package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.util.Direction;

public class Tank extends GameObject {

    private int bullets;
    private Direction lastMove;

    private int hp;

    public Tank(int xPos, int yPos, int hp) {
        this.width = GameParameters.TANKSIZE;
        this.height = GameParameters.TANKSIZE;
        this.bullets = 10;
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastMove = Direction.DOWN;
        this.hp = hp;
    }

    public Direction getLastMove() {
        return lastMove;
    }

    public void move(Direction direction, boolean changeDir) {
        switch (direction){
            case UP -> this.yPos -= GameParameters.TANKSPEED;
            case DOWN -> this.yPos += GameParameters.TANKSPEED;
            case RIGHT -> this.xPos += GameParameters.TANKSPEED;
            case LEFT -> this.xPos -= GameParameters.TANKSPEED;
        }
        if (changeDir) this.lastMove = direction;

    }

    public boolean takeDamage(){
        this.hp--;
        return hp > 0;
    }

    public int getHP(){
        return hp;
    }

    public Bullet shoot() {
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

