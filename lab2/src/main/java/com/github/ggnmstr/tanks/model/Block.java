package com.github.ggnmstr.tanks.model;

public class Block extends GameObject {

    private boolean isInvincible;
    private boolean isTransparent;
    public Block(int x, int y,boolean isInvincible, boolean isTransparent){
        this.isInvincible = isInvincible;
        this.isTransparent = isTransparent;
        this.xPos = x;
        this.yPos = y;
        this.height = GameParameters.BLOCKHEIGHT;
        this.width = GameParameters.BLOCKWIDTH;
    }

    public Block(int x, int y, int width, int height, boolean isInvincible){
        this.isInvincible = isInvincible;
        this.xPos = x;
        this.yPos = y;
        this.height = height;
        this.width = width;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

}
