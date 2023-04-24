package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.util.Direction;

// CR: better blocks destruction
public class Bullet extends GamePrimitive {
    private Direction direction;
    private Tank owner;

    public Bullet(int startX, int startY, Direction direction, Tank tank) {
        if (direction == Direction.DOWN || direction == Direction.UP) {
            this.width = GameParameters.BULLETSHORT;
            this.height = GameParameters.BULLETLONG;
        } else {
            this.width = GameParameters.BULLETLONG;
            this.height = GameParameters.BULLETSHORT;
        }
        this.xPos = startX;
        this.yPos = startY;
        this.direction = direction;
        this.owner = tank;
    }

    public void move() {
        switch (direction) {
            case UP -> yPos -= GameParameters.BULLETSPEED;
            case DOWN -> yPos += GameParameters.BULLETSPEED;
            case LEFT -> xPos -= GameParameters.BULLETSPEED;
            case RIGHT -> xPos += GameParameters.BULLETSPEED;
        }
    }

    public Tank getOwner() {
        return owner;
    }
}
