package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Presenter implements FieldListener {

    private BattleField battleField;
    private MainMenu mainMenu;

    private final Timer gameCycle = new Timer(1000/30, e -> updateGame());
    private final Timer enemySpawner = new Timer(4000, e -> battleField.spawnEnemy());

    private Direction lastDir = Direction.NONE;
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
        battleField.resetField();
        mainMenu.update(battleField.toGameObjects());
        gameCycle.start();
        enemySpawner.start();
        gameStarted = true;
    }

    public void startNewGame(String levelString){
        battleField = new BattleField(new FieldParameters(levelString,10));
        battleField.setFieldListener(this);
        startNewGame();
    }

    private void updateGame() {
        battleField.updateField();
        doMovement();
        mainMenu.update(battleField.toGameObjects());

    }

    private void doMovement() {
        if (lastDir == Direction.NONE) return;
        battleField.moveMainPlayer(lastDir);
    }

    public void responseToKey(int keyCode) {
        if (!gameStarted) return;
        switch (keyCode){
            case KeyEvent.VK_W -> {
                lastDir = Direction.UP;
            }
            case KeyEvent.VK_S -> {
                lastDir = Direction.DOWN;

            }
            case KeyEvent.VK_D -> {
                lastDir = Direction.RIGHT;

            }
            case KeyEvent.VK_A -> {
                lastDir = Direction.LEFT;
            }
            case KeyEvent.VK_SPACE -> {
                battleField.shootTank();
            }
        }
    }

    @Override
    public void gameLost(int score) {
        gameStarted = false;
        gameCycle.stop();
        enemySpawner.stop();
        mainMenu.launchEndgameMenu("You lost!",score);
    }

    @Override
    public void updateStats(int hp, int enemiesLeft,int score) {
        mainMenu.updateMenu(hp, enemiesLeft,score);
    }

    @Override
    public void gameWon(int score) {
        gameStarted = false;
        gameCycle.stop();
        enemySpawner.stop();
        mainMenu.launchEndgameMenu("You won!",score);
    }

    public void stopMovementKey(int keyCode) {
        Direction dir = keyToDirection(keyCode);
        if (dir == lastDir) lastDir = Direction.NONE;
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
        return Direction.NONE;
    }
}
