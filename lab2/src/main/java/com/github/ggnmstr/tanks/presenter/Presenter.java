package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.GameManager;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter {
    private MainMenu mainMenu;
    private GameManager gm;

    private Timer timer;

    public void setView(MainMenu mainView){
        this.mainMenu = mainView;
    }

    public void run(){
    }
    
    public void startNewGame(){
        gm = new GameManager();
        mainMenu.prepareGame();
        mainMenu.update(gm.getGVData());
        timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();

            }
        });
        timer.start();
    }

    private void updateGame() {
        gm.updateModel();
        mainMenu.update(gm.getGVData());

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
