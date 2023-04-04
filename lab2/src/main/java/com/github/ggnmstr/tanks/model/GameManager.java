package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static Presenter presenter;

    public List<GameObject> objList = new ArrayList<>();
    public List<GameObject> toRemove = new ArrayList<>();

    public GVData gvData = new GVData(objList);
    public BattleField battleField;

    public int score = 0;

    private static final GameManager instance = new GameManager();

    private GameManager(){}

    public static GameManager getInstance(){
        return instance;
    }

    public void initGameManager(Presenter presenter){
        score = 0;
        objList.clear();
        BattleField.enemies.clear();
        toRemove.clear();
        GameManager.presenter = presenter;
        battleField = new BattleField();
        battleField.initField();

    }

    public void destroy(GameObject x) {
        if (x instanceof Tank && !(x instanceof EnemyTank)){
            battleField.damageMainPlayer();
            presenter.updateStats(battleField.getHPleft());
            return;
        }
        if (x instanceof Base){
            getInstance().gameLost();
        }
        if (x instanceof Block b && b.isInvincible) return;
        if (x instanceof EnemyTank tank) {
            score+=100;
            presenter.updateStats(battleField.getEnemiesLeft(),score);
            tank.die();
            battleField.enemies.remove(tank);
        }
        toRemove.add(x);
    }

    public void gameLost() {
        presenter.gameLost(score);

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
