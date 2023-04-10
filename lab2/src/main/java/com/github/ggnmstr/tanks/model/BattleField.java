package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.util.MapCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class BattleField {

    private Block base;

    private Presenter presenter;
    private Tank mainPlayer;
    private final List<Tank> enemies = new ArrayList<>();

    public final List<EnemySpawnPoint> enemySpawnPoints = new ArrayList<>();

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();

    private final List<Bullet> bulletsToRemove = new ArrayList<>();

    private int playerSpawnX;
    private int playerSpawnY;

    private int enemiesSpawned = 0;
    private int enemiesKilled = 0;
    private int enemyLimit = 10;

    private int playerHP = 2;

    private int score = 0;

    private GVData gvData;


    private final char[][] mapTemplate = MapCreator.create();

    public void updateField(){
        moveBullets();
        moveEnemyTanks();

    }

    private void moveEnemyTanks() {
        for (Tank enemyTank : enemies){
            int decision = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            switch (decision){
                case 2 -> {
                    moveTank(enemyTank,Direction.UP);
                }
                case 3 -> {
                    moveTank(enemyTank,Direction.DOWN);
                }
                case 4 -> {
                    moveTank(enemyTank,Direction.LEFT);
                }
                case 5 -> {
                    moveTank(enemyTank,Direction.RIGHT);
                }
                case 6 -> {
                    Bullet bullet = enemyTank.shoot();
                    if (bullet == null) return;
                    bullets.add(bullet);
                }
            }
        }
    }

    private void moveBullets() {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();){
            Bullet bullet = iterator.next();
            bullet.move();
            if (bulletHasCollision(bullet)){
                iterator.remove();
            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    private boolean bulletHasCollision(Bullet bullet) {
        boolean flag = false;
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext(); ) {
            Block block = iterator.next();
            if (isCollision(bullet, block)) {
                if (block == base){
                    presenter.gameLost(score);
                    return true;
                }
                if (!block.isInvincible()) {
                    iterator.remove();
                }
                flag = true;
            }
        }
        // TODO: How to add ability to destroy bullet using other bullet?
        // version without bulletsToRemove is broken (ConcurentModificationException)
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();){
            Bullet otherbullet = iterator.next();
            if (otherbullet != bullet && isCollision(bullet,otherbullet)){
                bulletsToRemove.add(otherbullet);
                return true;
            }
        }
        for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext();){
            Tank enemy = iterator.next();
            if (isCollision(bullet,enemy)){
                score+=100;
                enemiesKilled++;
                presenter.updateStats(getEnemiesLeft(),score);
                if (enemiesKilled == enemyLimit){
                    presenter.gameWon(score);
                }
                iterator.remove();
                return true;
            }
        }
        if (isCollision(bullet,mainPlayer)){
            damageMainPlayer();
            presenter.updateStats(getHPleft());
            return true;
        }
        return flag;
    }

    public void initField(){
        clearMap();
        buildMap();
        score = 0;
        mainPlayer = new Tank(playerSpawnX,playerSpawnY);
        gvData = new GVData(mainPlayer,base,blocks,enemies,bullets);
        generateBorders();
        spawnEnemy();
        presenter.updateStats(getHPleft());
        presenter.updateStats(getEnemiesLeft(),score);

    }

    private void clearMap() {
        enemiesKilled = 0;
        enemiesSpawned = 0;
        blocks.clear();
        enemies.clear();
        bullets.clear();
        enemySpawnPoints.clear();
        bulletsToRemove.clear();
    }

    public void setPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public int getEnemiesLeft(){
        return enemyLimit - enemiesSpawned;
    }

    public void spawnEnemy(){
        if (enemiesSpawned >= enemyLimit) return;
        Tank newEnemy = enemySpawnPoints.get(enemiesSpawned % enemySpawnPoints.size()).spawnEnemyTank();
        if (newEnemy == null) return;
        enemiesSpawned++;
        presenter.updateStats(getEnemiesLeft(),score);
        enemies.add(newEnemy);
    }

    void generateBorders(){
        Block left = new Block(0,0,0,GameParameters.FIELDWIDTH,true);
        Block top = new Block(0,0,GameParameters.FIELDHEIGHT,0,true);
        Block bottom = new Block(0,GameParameters.FIELDHEIGHT,GameParameters.FIELDWIDTH,0,true);
        Block right = new Block(GameParameters.FIELDWIDTH,0,0,GameParameters.FIELDHEIGHT,true);
        blocks.add(left);
        blocks.add(right);
        blocks.add(top);
        blocks.add(bottom);
    }

    void buildMap(){
        for (int i = 0; i < mapTemplate.length; i++){
            for (int j = 0; j < mapTemplate[i].length; j++){
                if (mapTemplate[i][j] == '0') continue;
                if (mapTemplate[i][j] == '7'){
                    Block block = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,true);
                    blocks.add(block);
                }
                if (mapTemplate[i][j] == '1'){
                    Block block = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,false);
                    blocks.add(block);
                }
                if (mapTemplate[i][j] == '2'){
                    EnemySpawnPoint spawnPoint = new EnemySpawnPoint(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT);
                    enemySpawnPoints.add(spawnPoint);
                }
                if (mapTemplate[i][j] == '3'){
                    base = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,
                            GameParameters.BASEWIDTH,GameParameters.BASEHEIGHT,false);
                    blocks.add(base);
                }
                if (mapTemplate[i][j] == '4'){
                    playerSpawnX = j * GameParameters.BLOCKWIDTH;
                    playerSpawnY = i * GameParameters.BLOCKHEIGHT;
                }

            }
        }
    }

    public void damageMainPlayer() {
        if (playerHP <= 0) presenter.gameLost(score);
        playerHP--;
        respawnPlayer();

    }

    private void respawnPlayer() {
        mainPlayer.xPos = playerSpawnX;
        mainPlayer.yPos = playerSpawnY;
    }

    public int getHPleft() {
        return playerHP;
    }

    public static boolean isCollision(GameObject o1, GameObject o2){
        return o1.xPos < o2.xPos + o2.width &&
                o1.xPos + o1.width > o2.xPos &&
                o1.yPos < o2.yPos + o2.height &&
                o1.height + o1.yPos > o2.yPos;
    }

    private void moveTank(Tank tank, Direction direction){
        tank.move(direction,true);
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();){
            Block block = iterator.next();
            if (isCollision(tank,block)){
                tank.move(Direction.getOpposite(direction),false);
                return;
            }
        }
        for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext();){
            Tank enemytank = iterator.next();
            if (tank != enemytank && isCollision(tank,enemytank)){
                tank.move(Direction.getOpposite(direction),false);
                return;
            }
        }
        if (tank != mainPlayer && isCollision(tank,mainPlayer)){
            tank.move(Direction.getOpposite(direction),false);
        }
    }

    public void moveMainPlayer(Direction direction){
        moveTank(mainPlayer,direction);
    }

    public void shootTank() {
        Bullet bullet = mainPlayer.shoot();
        if (bullet != null) bullets.add(bullet);
    }

    public GVData getGvData() {
        return gvData;
    }
}
