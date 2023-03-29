package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.GameManager;
import com.github.ggnmstr.tanks.view.GameView;
import com.github.ggnmstr.tanks.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter {
    private MainView mainView;
    private GameManager gm;

    private Timer timer;

    public void setView(MainView mainView){
        this.mainView = mainView;
    }

    public void run(){
    }
    
    public void startNewGame(){
        gm = new GameManager();
        mainView.prepareGame();
        mainView.update(gm.toGVData());
        timer = new Timer(1000 / 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();

            }
        });
        timer.start();
    }

    private void updateGame() {
        gm.updateModel();
        mainView.update(gm.toGVData());

    }

    public void responseToKey(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                gm.moveMainPlayer(0,-20);
            }
            case "move down"-> {
                gm.moveMainPlayer(0,20);

            }
            case "move right" -> {
                gm.moveMainPlayer(20,0);

            }
            case "move left" -> {
                gm.moveMainPlayer(-20,0);
            }
            case "shoot" -> {
                gm.shootTank();
            }
        }
    }
}
