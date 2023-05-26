package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.BlockObject;

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

    public FieldBlock(int x, int y, int width, int height, boolean isInvincible, boolean isTransparent){
        this.isInvincible = isInvincible;
        this.isTransparent = isTransparent;
        this.xPos = x;
        this.yPos = y;
        this.height = height;
        this.width = width;
    }

    public static FieldBlock from(BlockObject blockObject) {
        return new FieldBlock(blockObject.x(), blockObject.y(), !blockObject.isDestructible(), blockObject.isTransparent());
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

}
