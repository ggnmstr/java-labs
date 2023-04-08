package com.github.ggnmstr.tanks.model;

public class Base extends Block {
    private boolean isAlive;
    public Base(int x, int y){
        super(x,y,false);
        this.isAlive = true;
        this.height = GameParameters.BASEHEIGHT;
        this.width = GameParameters.BASEWIDTH;
    }

    public boolean isAlive(){
        return isAlive;
    }
}
