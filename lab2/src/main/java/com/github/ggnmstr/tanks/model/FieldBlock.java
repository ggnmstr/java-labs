package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.BlockObject;

public class FieldBlock extends GamePrimitive {

    private final boolean isInvincible;
    private final boolean isTransparent;

    public FieldBlock(int x, int y, int width, int height, boolean isInvincible, boolean isTransparent){
        this.isInvincible = isInvincible;
        this.isTransparent = isTransparent;
        this.xPos = x;
        this.yPos = y;
        this.height = height;
        this.width = width;
    }

    public static FieldBlock from(BlockObject blockObject) {
        return new FieldBlock(blockObject.x(), blockObject.y(), blockObject.width(), blockObject.height(), !blockObject.isDestructible(), blockObject.isTransparent());
    }

    public boolean isDestructible() {
        return !isInvincible;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

}
