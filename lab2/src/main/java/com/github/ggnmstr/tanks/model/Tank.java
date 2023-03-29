package com.github.ggnmstr.tanks.model;

public class Tank extends GameObject {
    private int bullets;
    private int health;

    public Tank(int xPos, int yPos){
        this.width = 100;
        this.height = 100;
        this.health = 100;
        this.bullets = 10;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void move(int xDelta, int yDelta){
        this.xPos += xDelta;
        this.yPos += yDelta;
    }

    void shoot(){
        if (bullets == 0) return;
        bullets--;
    }
}

