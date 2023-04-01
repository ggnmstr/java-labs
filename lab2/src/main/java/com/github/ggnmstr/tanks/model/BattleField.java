package com.github.ggnmstr.tanks.model;

import java.util.ArrayList;
import java.util.List;

public class BattleField {
    public Tank mainPlayer;
    public static List<EnemyTank> enemies = new ArrayList<>();

    public static List<EnemySpawnPoint> enemySpawnPoints = new ArrayList<>();


    // 26 (width) x 56 (height)
    // 0 - empty, 1 - wall, 2 - enemy spawn point
    private int[][] mapTemplate = {
        {0,0,0,0,0,0,0,0,0,0,0,0,2},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,1,0,1,0,0,0,0,0,1,0,1,0},
        {0,0,0,0,0,1,0,1,0,0,0,0,0},
        {1,0,1,1,0,0,0,0,0,1,1,0,1},
        {0,1,0,1,0,0,0,0,0,1,0,1,0},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,1,0,1,0,1,0,1,0,1,0,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,2},

    };

    private int[][] mapwithborders = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,0,0,0,0,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,0,0,0,1},
            {1,1,0,1,1,0,0,0,0,0,1,1,0,1,1},
            {1,0,1,0,1,0,0,0,0,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

    };


    void initField(){
        buildMap();
        mainPlayer = new Tank(0,0);
        enemies.add(new EnemyTank(500,500));
        GameManager.objList.add(this.mainPlayer);
        GameManager.objList.add(enemies.get(0));
    }

    public void updateField(){
        if (enemies.size() == 0){
            EnemySpawnPoint spawnPoint = enemySpawnPoints.get(0);
            spawnPoint.spawnEnemyTank();
        }
        for (EnemyTank x : enemies){
            x.makeMove();
        }
    }

    void buildMap(){
        for (int i = 0; i < mapTemplate.length; i++){
            for (int j = 0; j < mapTemplate[i].length; j++){
                if (mapTemplate[i][j] == 0) continue;
                if (mapTemplate[i][j] == 1){
                    Block block = new Block(j*80,i*60,false);
                    GameManager.objList.add(block);
                }
                if (mapTemplate[i][j] == 2){
                    EnemySpawnPoint spawnPoint = new EnemySpawnPoint(j*80,i*60);
                    this.enemySpawnPoints.add(spawnPoint);
                }

            }
        }
    }
}
