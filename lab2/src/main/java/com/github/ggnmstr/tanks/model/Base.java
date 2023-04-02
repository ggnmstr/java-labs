package com.github.ggnmstr.tanks.model;

public class Base extends GameObject {
    private boolean isAlive;
    public Base(int x, int y){
        this.isAlive = true;
        this.xPos = x;
        this.yPos = y;
        this.height = GameParameters.BASEHEIGHT;
        this.width = GameParameters.BASEWIDTH;
    }

    public boolean isAlive(){
        return isAlive;
    }
}
