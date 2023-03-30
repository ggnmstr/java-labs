package com.github.ggnmstr.tanks.model;

public class BattleField {
    public Tank mainPlayer;
    public Tank enemyPlayer;
    void initField(){
        mainPlayer = new Tank(0,0);
        enemyPlayer = new Tank(500,500);
    }
}
