package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.Direction;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Presenter {

    private BattleField battleField;
    private MainMenu mainMenu;

    private final Timer gameCycle = new Timer(1000/30, e -> updateGame());
    private final Timer enemySpawner = new Timer(4000, e -> battleField.spawnEnemy());
//    private final Timer playerMover = new Timer(1000/30, e -> doMovement());

    private Direction lastDir;
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
//        playerMover.start();
        gameStarted = true;
    }

    private void updateGame() {
        battleField.updateField();
        doMovement();
        mainMenu.update(battleField.getGvData());

    }

    private void doMovement() {
        if (lastDir == null) return;
        battleField.moveMainPlayer(lastDir);
    }

    public void responseToKey(int keyCode) {
        if (!gameStarted) return;
        switch (keyCode){
            case KeyEvent.VK_W -> {
                lastDir = Direction.UP;
                //battleField.moveMainPlayer(Direction.UP);
            }
            case KeyEvent.VK_S -> {
                lastDir = Direction.DOWN;
                //battleField.moveMainPlayer(Direction.DOWN);

            }
            case KeyEvent.VK_D -> {
                lastDir = Direction.RIGHT;
                //battleField.moveMainPlayer(Direction.RIGHT);

            }
            case KeyEvent.VK_A -> {
                lastDir = Direction.LEFT;
                //battleField.moveMainPlayer(Direction.LEFT);
            }
            case KeyEvent.VK_SPACE -> {
                battleField.shootTank();
            }
        }
    }

    public void gameLost(int score) {
        gameStarted = false;
        gameCycle.stop();
        enemySpawner.stop();
//        playerMover.stop();
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
//        playerMover.stop();
        mainMenu.launchEndgameMenu("You won!",score);
    }

    public void stopMovementKey(int keyCode) {
        Direction dir = keyToDirection(keyCode);
        if (dir == lastDir) lastDir = null;
    }

    private Direction keyToDirection(int keycode){
        switch (keycode){
            case KeyEvent.VK_W -> {
                return Direction.UP;
            }
            case KeyEvent.VK_A -> {
                return Direction.LEFT;
            }
            case KeyEvent.VK_S -> {
                return Direction.DOWN;
            }
            case KeyEvent.VK_D -> {
                return Direction.RIGHT;
            }
        }
        return Direction.DOWN;
    }
}
