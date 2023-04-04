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
        GameManager.getInstance().initGameManager(this);
        mainMenu.prepareGame();
        mainMenu.update(GameManager.getInstance().getGVData());
        gameCycle = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();

            }
        });
        enemySpawner = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getInstance().battleField.spawnEnemy();
            }
        });
        gameCycle.start();
        enemySpawner.start();
    }

    private void updateGame() {
        GameManager.getInstance().updateModel();
        mainMenu.update(GameManager.getInstance().getGVData());

    }

    public void responseToKey(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                GameManager.getInstance().moveMainPlayer(0,-15);
            }
            case "move down"-> {
                GameManager.getInstance().moveMainPlayer(0,15);

            }
            case "move right" -> {
                GameManager.getInstance().moveMainPlayer(15,0);

            }
            case "move left" -> {
                GameManager.getInstance().moveMainPlayer(-15,0);
            }
            case "shoot" -> {
                GameManager.getInstance().shootTank();
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

    public void updateStats(int hPleft) {
        mainMenu.updateMenu(hPleft);
    }
}
