package com.github.ggnmstr.tanks.model;

// CR: merge with user tank spawn
public class EnemySpawnPoint extends GamePrimitive {
    public EnemySpawnPoint(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.height = GameParameters.TANKSIZE;
        this.width = GameParameters.TANKSIZE;
    }

    public Tank spawnTank() {
        Tank newEnemyTank = new Tank(this.xPos,this.yPos,GameParameters.ENEMYHP);
        return newEnemyTank;
    }
}
