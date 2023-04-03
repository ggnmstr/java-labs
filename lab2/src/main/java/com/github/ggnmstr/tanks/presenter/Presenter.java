package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.GameManager;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter {
    private MainMenu mainMenu;

    private Timer gameCycle;
    private Timer enemySpawner;

    public void setView(MainMenu mainView){
        this.mainMenu = mainView;
    }

    public void run(){
    }
    
    public void startNewGame(){
        GameManager.initGameManager(this);
        mainMenu.prepareGame();
        mainMenu.update(GameManager.getGVData());
        gameCycle = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();

            }
        });
        enemySpawner = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.battleField.spawnEnemy();
            }
        });
        gameCycle.start();
        enemySpawner.start();
    }

    private void updateGame() {
        GameManager.updateModel();
        mainMenu.update(GameManager.getGVData());

    }

    public void responseToKey(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                GameManager.moveMainPlayer(0,-15);
            }
            case "move down"-> {
                GameManager.moveMainPlayer(0,15);

            }
            case "move right" -> {
                GameManager.moveMainPlayer(15,0);

            }
            case "move left" -> {
                GameManager.moveMainPlayer(-15,0);
            }
            case "shoot" -> {
                GameManager.shootTank();
            }
        }
    }

    public void gameLost(int score) {
        gameCycle.stop();
        enemySpawner.stop();
        mainMenu.launchLostMenu(score);
    }

    public void updateStats(int enemiesLeft,int score) {
        mainMenu.updateMenu(enemiesLeft,score);
    }
}
