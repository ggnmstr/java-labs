package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static Presenter presenter;

    public static List<GameObject> objList = new ArrayList<>();
    public static List<GameObject> toRemove = new ArrayList<>();

    public static GVData gvData;
    public static BattleField battleField;
    public GameManager(Presenter presenter){
        GameManager.presenter = presenter;
        gvData = new GVData(objList);
        battleField = new BattleField();
        battleField.initField();
    }

    public static void initGameManager(Presenter presenter){
        GameManager.presenter = presenter;
        gvData = new GVData(objList);
        battleField = new BattleField();
        battleField.initField();

    }

    public static void destroy(GameObject x) {
        if (x instanceof Base){
            gameLost();
        }
        if (x instanceof Block b && b.isInvincible) return;
        if (x instanceof EnemyTank tank) {
            tank.die();
            battleField.enemies.remove(tank);
        }
        toRemove.add(x);
    }

    private static void gameLost() {
        presenter.gameLost();

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
        battleField.updateField();
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
