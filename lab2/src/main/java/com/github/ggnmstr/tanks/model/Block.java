package com.github.ggnmstr.tanks.model;

public class Block extends GameObject {
    private boolean isInvincible;
    public Block(int x, int y,boolean isInvincible){
        this.isInvincible = isInvincible;
        this.xPos = x;
        this.yPos = y;
        this.height = 10;
        this.width = 40;
    }
}
