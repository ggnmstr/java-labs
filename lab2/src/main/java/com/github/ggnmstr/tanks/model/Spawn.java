package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.EnemySpawnObject;

public final class Spawn extends GamePrimitive {

    public Spawn(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.width = GameParameters.TANKSIZE;
        this.height = GameParameters.TANKSIZE;
    }

    public static Spawn from(EnemySpawnObject enemySpawnObject) {
        return new Spawn(enemySpawnObject.x(), enemySpawnObject.y());
    }
}