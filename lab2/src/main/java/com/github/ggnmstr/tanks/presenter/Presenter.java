package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.Direction;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Presenter {

    private BattleField battleField;
    private MainMenu mainMenu;

    private final Timer gameCycle = new Timer(1000/60, e -> updateGame());
    private final Timer enemySpawner = new Timer(5000, e -> battleField.spawnEnemy());
    private boolean gameStarted = false;

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
        battleField.initField();
        mainMenu.update(battleField.getGvData());
        gameCycle.start();
        enemySpawner.start();
        gameStarted = true;
    }

    private void updateGame() {
        battleField.updateField();
        mainMenu.update(battleField.getGvData());

    }

    public void responseToKey(int keyCode) {
        if (!gameStarted) return;
        switch (keyCode){
            case KeyEvent.VK_W -> {
                battleField.moveMainPlayer(Direction.UP);
            }
            case KeyEvent.VK_S -> {
                battleField.moveMainPlayer(Direction.DOWN);

            }
            case KeyEvent.VK_D -> {
                battleField.moveMainPlayer(Direction.RIGHT);

            }
            case KeyEvent.VK_A -> {
                battleField.moveMainPlayer(Direction.LEFT);
            }
            case KeyEvent.VK_SPACE -> {
                battleField.shootTank();
            }
        }
    }

    /*
    public void responseToKey(String actionCommand) {
        switch (actionCommand){
            case "move up" -> {
                battleField.moveMainPlayer(Direction.UP);
            }
            case "move down"-> {
                battleField.moveMainPlayer(Direction.DOWN);

            }
            case "move right" -> {
                battleField.moveMainPlayer(Direction.RIGHT);

            }
            case "move left" -> {
                battleField.moveMainPlayer(Direction.LEFT);
            }
            case "shoot" -> {
                battleField.shootTank();
            }
        }
    }
    */
    public void gameLost(int score) {
        gameStarted = false;
        gameCycle.stop();
        enemySpawner.stop();
        mainMenu.launchEndgameMenu("You lost!",score);
    }

    public void updateStats(int enemiesLeft,int score) {
        mainMenu.updateMenu(enemiesLeft,score);
    }

    public void updateStats(int hPleft) {
        mainMenu.updateMenu(hPleft);
    }

    public void gameWon(int score) {
        gameStarted = false;
        gameCycle.stop();
        enemySpawner.stop();
        mainMenu.launchEndgameMenu("You won!",score);
    }
}
