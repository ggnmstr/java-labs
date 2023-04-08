package com.github.ggnmstr.tanks.model;

import java.util.List;

/// TODO: think about it
/// VERY IMPORTANT QUESTION:
/// Should I make static fields (like now I have static lists in BattleField and GameManager
/// Or should I connect classes using fields (for example, each EnemySpawnPoint will have
/// field "BattleField field" that it belongs to
public class EnemySpawnPoint extends GameObject {
    public EnemySpawnPoint(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.height = 0;
        this.width = 0;
    }

    public void spawnEnemyTank(List<EnemyTank> tanks){
        EnemyTank newEnemyTank = new EnemyTank(this.xPos,this.yPos);
        tanks.add(newEnemyTank);
    }
}
