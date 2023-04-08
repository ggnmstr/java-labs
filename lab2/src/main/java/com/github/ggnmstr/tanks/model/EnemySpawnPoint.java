package com.github.ggnmstr.tanks.model;

public class EnemySpawnPoint extends GameObject {
    public EnemySpawnPoint(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.height = 0;
        this.width = 0;
    }

    public Tank spawnEnemyTank(){
        Tank newEnemyTank = new Tank(this.xPos,this.yPos);
        return newEnemyTank;
    }
}
