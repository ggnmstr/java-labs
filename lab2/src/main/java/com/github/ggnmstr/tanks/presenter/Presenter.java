package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.dto.LevelObject;
import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.view.MainMenu;

import javax.swing.*;

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

    public void run(){
    }
    
    public void startNewGame(){
        mainMenu.prepareGame();
        battleField = new BattleField(battleField.initialConfig());
        battleField.setFieldListener(this);
        mainMenu.update(battleField.toGameObjects());
        gameCycle.start();
        enemySpawner.start();
        gameStarted = true;
    }

    public void startNewGame(String levelString){
        battleField = new BattleField(LevelObject.fromFile(levelString + ".json"));
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


    public void setDirection(Direction newDirection) {
        if (!gameStarted) return;
        lastDir = newDirection;
    }

    public void shoot() {
        battleField.shootTank();
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

    public void stopMovementDirection(Direction dir) {
        if (dir == lastDir) lastDir = Direction.NONE;
    }
}
