package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.GameManager;
import com.github.ggnmstr.tanks.model.Tank;
import com.github.ggnmstr.tanks.view.MainView;

public class Presenter {
    private MainView mainView;
    private GameManager gm;

    public void setView(MainView mainView){
        this.mainView = mainView;
    }

    public void run(){
    }
    
    public void startNewGame(){
        gm = new GameManager();
        mainView.prepareGame();
        mainView.update(gm.toGVData());
    }

    public void moveTank(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                gm.moveMainPlayer(0,-20);
                mainView.update(gm.toGVData());
            }
            case "move down"-> {
                gm.moveMainPlayer(0,20);
                mainView.update(gm.toGVData());

            }
            case "move right" -> {
                gm.moveMainPlayer(20,0);
                mainView.update(gm.toGVData());

            }
            case "move left" -> {
                gm.moveMainPlayer(-20,0);
                mainView.update(gm.toGVData());
            }
        }
    }
}
