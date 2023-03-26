package com.github.ggnmstr.tanks.model;

public class Tank {
    private int bullets;
    private int health;

    private int xPos, yPos;

    public Tank(int xPos, int yPos){
        this.health = 100;
        this.bullets = 10;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    void move(int xDelta, int yDelta){
        this.xPos += xDelta;
        this.yPos += yDelta;
    }

    void shoot(){
        if (bullets == 0) return;
        bullets--;
    }
}

