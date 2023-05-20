package com.github.ggnmstr.tanks.model;

public class FieldBlock extends GamePrimitive {

    private final boolean isInvincible;
    // CR: move bushes to view
    private final boolean isTransparent;

    public FieldBlock(int x, int y, boolean isInvincible, boolean isTransparent){
        this.isInvincible = isInvincible;
        this.isTransparent = isTransparent;
        this.xPos = x;
        this.yPos = y;
        this.height = GameParameters.BLOCKHEIGHT;
        this.width = GameParameters.BLOCKWIDTH;
    }

    public FieldBlock(int x, int y, int width, int height, boolean isInvincible){
        this.isInvincible = isInvincible;
        this.isTransparent = false;
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
