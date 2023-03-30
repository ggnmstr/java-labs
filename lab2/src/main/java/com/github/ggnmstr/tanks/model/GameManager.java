package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public List<GameObject> objlist = new ArrayList<>();

    public GVData gvData;
    public BattleField battleField;
    public GameManager(){
        gvData = new GVData(objlist);
        battleField = new BattleField();
        battleField.initField();
        objlist.add(battleField.mainPlayer);
        objlist.add(battleField.enemyPlayer);
    }


    public GVData getGVData(){
        return gvData;
    }

    public void moveMainPlayer(int x, int y){
        battleField.mainPlayer.move(x,y);
    }

    public void shootTank(){
        battleField.mainPlayer.shoot();
        Bullet bullet = new Bullet(battleField.mainPlayer.getxPos()+50,
                battleField.mainPlayer.getyPos()+100,battleField.mainPlayer.getLastMove());
        System.out.println(bullet.getxPos()+ ' ' +bullet.getyPos());
        objlist.add(bullet);

    }

    public void updateModel() {
        for (GameObject x : objlist){
            if (x instanceof Bullet bullet){
                bullet.move();
            }
        }
    }
}
