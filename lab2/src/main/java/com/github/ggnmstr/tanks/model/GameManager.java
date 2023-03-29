package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;

public class GameManager {
    public BattleField battleField;
    public GameManager(){
        battleField = new BattleField();
        battleField.initField();
    }

    public void performAction(){

    }

    public GVData toGVData(){

        return new GVData(battleField.mainPlayer.getxPos(),battleField.mainPlayer.getyPos());
    }

    public void moveMainPlayer(int x, int y){
        battleField.mainPlayer.move(x,y);
    }

}
