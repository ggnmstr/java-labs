package com.github.ggnmstr.tanks.model;

public class BattleField {
    public Tank mainPlayer;
    public Tank enemyPlayer;
    void initField(){
        buildMap();
        mainPlayer = new Tank(0,0);
        enemyPlayer = new Tank(500,500);
    }

    void buildMap(){
        Block block = new Block(20,20,false);
        GameManager.objList.add(block);
    }
}
