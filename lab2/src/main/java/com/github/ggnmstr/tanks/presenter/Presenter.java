package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.Tank;
import com.github.ggnmstr.tanks.view.MainView;

public class Presenter {
    private MainView mainView;

    private Tank tank;

    public void setView(MainView mainView){
        this.mainView = mainView;
    }

    public void run(){
    }
    
    public void startNewGame(){
        tank = new Tank(10,10);
        mainView.prepareGame();
        mainView.update(tank);
    }

    public void moveTank(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                tank.move(0,-20);
                mainView.update(tank);
            }
            case "move down"-> {
                tank.move(0,20);
                mainView.update(tank);

            }
            case "move right" -> {
                tank.move(20,0);
                mainView.update(tank);

            }
            case "move left" -> {
                tank.move(-20,0);
                mainView.update(tank);
            }
        }
    }

    public Tank getTank(){return  tank;}
}
