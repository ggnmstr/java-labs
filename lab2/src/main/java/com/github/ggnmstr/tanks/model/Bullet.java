package com.github.ggnmstr.tanks.model;

public class Bullet extends GameObject {
    private Direction direction;

    public Bullet(int startX, int startY, Direction direction){
        if (direction == Direction.DOWN || direction == Direction.UP){
            this.width = 10;
            this.height = 40;
        } else {
            this.width = 40;
            this.height = 10;
        }
        this.xPos = startX;
        this.yPos = startY;
        this.direction = direction;
    }

    public void move(){
        switch (direction){
            case UP -> yPos-=10;
            case DOWN -> yPos +=10;
            case LEFT -> xPos -= 10;
            case RIGHT -> xPos += 10;
        }
    }
}
