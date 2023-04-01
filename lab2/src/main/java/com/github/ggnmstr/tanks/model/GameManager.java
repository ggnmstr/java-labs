package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static List<GameObject> objList = new ArrayList<>();
    public static List<GameObject> toRemove = new ArrayList<>();
    //public static List<GameObject> bulletlist = new ArrayList<>();

    public GVData gvData;
    public BattleField battleField;
    public GameManager(){
        gvData = new GVData(objList);
        battleField = new BattleField();
        battleField.initField();
        objList.add(battleField.mainPlayer);
        objList.add(battleField.enemyPlayer);
    }

    public static void destroy(GameObject x) {
        if (x instanceof EnemyTank tank) tank.die();
        toRemove.add(x);
    }


    public GVData getGVData(){
        return gvData;
    }

    public void moveMainPlayer(int x, int y){
        battleField.mainPlayer.move(x,y);
    }

    public void shootTank(){
        battleField.mainPlayer.shoot();
    }

    public void updateModel() {
        battleField.enemyPlayer.makeMove();
        objList.removeAll(toRemove);
        toRemove.clear();
        for (GameObject x : objList){
            if (x instanceof Bullet bullet){
                bullet.move();
            }
        }
    }

    static boolean isCollision(GameObject o1, GameObject o2){
        return o1.xPos < o2.xPos + o2.width &&
                o1.xPos + o1.width > o2.xPos &&
                o1.yPos < o2.yPos + o2.height &&
                o1.height + o1.yPos > o2.yPos;
    }
}
