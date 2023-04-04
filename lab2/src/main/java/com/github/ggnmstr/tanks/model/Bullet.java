package com.github.ggnmstr.tanks.model;

public class Bullet extends GameObject {
    private Direction direction;
    //private Tank owner;

    public Bullet(int startX, int startY, Direction direction) {
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
    }

    public void move() {
        switch (direction) {
            case UP -> yPos -= GameParameters.BULLETSPEED;
            case DOWN -> yPos += GameParameters.BULLETSPEED;
            case LEFT -> xPos -= GameParameters.BULLETSPEED;
            case RIGHT -> xPos += GameParameters.BULLETSPEED;
        }
        for (GameObject x : GameManager.getInstance().objList) {
            if (this != x && GameManager.isCollision(this, x)) {
                GameManager.getInstance().destroy(this);
                GameManager.getInstance().destroy(x);
            }
        }
    }
}
