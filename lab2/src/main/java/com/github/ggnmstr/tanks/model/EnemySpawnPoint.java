package com.github.ggnmstr.tanks.model;

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

    public void spawnEnemyTank(){
        EnemyTank newEnemyTank = new EnemyTank(this.xPos,this.yPos);
        GameManager.objList.add(newEnemyTank);
        BattleField.enemies.add(newEnemyTank);
    }
}
