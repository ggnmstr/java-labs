package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;

public class Presenter {

    private BattleField battleField;
    private MainMenu mainMenu;

    private Timer gameCycle;
    private Timer enemySpawner;

    public void setView(MainMenu mainView){
        this.mainMenu = mainView;
    }

    public Presenter(BattleField battleField){
        this.battleField = battleField;
    }

    public void run(){
    }
    
    public void startNewGame(){
        mainMenu.prepareGame();
        mainMenu.update(battleField.getGvData());
        gameCycle = new Timer(1000 / 60, e -> {
            updateGame();
        });
        enemySpawner = new Timer(5000, e -> {
            battleField.spawnEnemy();
        });
        gameCycle.start();
        enemySpawner.start();
    }

    private void updateGame() {
        battleField.updateField();
        mainMenu.update(battleField.getGvData());

    }

    public void responseToKey(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                // CR: call field methods
                battleField.moveMainPlayer(0,-15);
            }
            case "move down"-> {
                battleField.moveMainPlayer(0,15);

            }
            case "move right" -> {
                battleField.moveMainPlayer(15,0);

            }
            case "move left" -> {
                battleField.moveMainPlayer(-15,0);
            }
            case "shoot" -> {
                battleField.shootTank();
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

    public void gameWon(int score) {

    }
}
