package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.dto.Position;
import com.github.ggnmstr.tanks.dto.TankModel;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.MapCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


// CR: pass game data and write a test
public class BattleField {

    private Block base;

    private FieldListener fieldListener;

    private Tank mainPlayer;
    private final List<Tank> enemies = new ArrayList<>();

    public final List<EnemySpawnPoint> enemySpawnPoints = new ArrayList<>();

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();

    private final List<Bullet> bulletsToRemove = new ArrayList<>();

    // CR: Spawn
    private int playerSpawnX;
    private int playerSpawnY;

    private int enemiesLeft;

    private int score = 0;


    private final char[][] mapTemplate = MapCreator.create();

    public void updateField(){
        moveBullets();
        moveEnemyTanks();

    }

    private void moveEnemyTanks() {
        for (Tank enemyTank : enemies){
            Direction dir = enemyTank.getLastMove();
            if (ThreadLocalRandom.current().nextInt(0,2) == 0){
            //if (enemyTank.getyPos() >= mainPlayer.getyPos()-50 && enemyTank.getyPos() <= mainPlayer.getyPos() + 50){
                Bullet bullet = enemyTank.shoot();
                if (bullet != null) bullets.add(bullet);
            }

            if (moveTank(enemyTank,dir)) continue;
            else dir = Direction.values()[ThreadLocalRandom.current().nextInt(0,4)];
            moveTank(enemyTank,dir);
        }
    }

    private void moveBullets() {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();){
            Bullet bullet = iterator.next();
            bullet.move();
            if (bulletHasCollision(bullet)){
                bullet.getOwner().makeShootable();
                iterator.remove();
            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    private boolean bulletHasCollision(Bullet bullet) {
        boolean flag = false;
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext(); ) {
            Block block = iterator.next();
            if (block.isTransparent()) continue;
            if (isCollision(bullet, block)) {
                if (block == base){
                    fieldListener.gameLost(score);
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
            if (otherbullet != bullet && isCollision(bullet, otherbullet)){
                otherbullet.getOwner().makeShootable();
                bulletsToRemove.add(otherbullet);
                return true;
            }
        }
        if (bullet.getOwner() == mainPlayer){
            for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext();){
                Tank enemy = iterator.next();
                if (isCollision(bullet,enemy)){
                    score+=100;
                    fieldListener.updateStats(mainPlayer.getHP(), enemiesLeft,score);
                    iterator.remove();
                    if (enemiesLeft == 0 && enemies.isEmpty()){
                        fieldListener.gameWon(score);
                    }
                    return true;
                }
            }
        }

        if (isCollision(bullet,mainPlayer)){
            if (!mainPlayer.takeDamage()) fieldListener.gameLost(score);
            fieldListener.updateStats(mainPlayer.getHP(),enemiesLeft,score);
            respawnPlayer();
            return true;
        }
        return flag;
    }

    public void initField(){
        enemiesLeft = 10;
        clearMap();
        buildMap();
        score = 0;
        mainPlayer = new Tank(playerSpawnX,playerSpawnY,GameParameters.PLAYERHP);
        generateBorders();
        spawnEnemy();
        fieldListener.updateStats(mainPlayer.getHP(),enemiesLeft,score);
    }

    private void clearMap() {
        blocks.clear();
        enemies.clear();
        bullets.clear();
        enemySpawnPoints.clear();
        bulletsToRemove.clear();
    }

    public void setFieldListener(FieldListener fieldListener){
        this.fieldListener = fieldListener;
    }

    public void spawnEnemy(){
        if (enemiesLeft <= 0) return;
        int n = enemySpawnPoints.size();
        EnemySpawnPoint point = enemySpawnPoints.get(ThreadLocalRandom.current().nextInt(0,n));
        Tank newEnemy = null;
        if (!pointOccupied(point)) newEnemy = point.spawnEnemyTank();
        if (newEnemy == null) return;
        enemiesLeft--;
        fieldListener.updateStats(mainPlayer.getHP(),enemiesLeft,score);
        enemies.add(newEnemy);
    }

    private boolean pointOccupied(EnemySpawnPoint point){
        if (isCollision(mainPlayer,point)) return true;
        for (Tank enemy : enemies){
            if (isCollision(enemy,point)) return true;
        }
        return false;
    }

    void generateBorders(){
        Block left = new Block(-15,0,15,GameParameters.FIELDWIDTH,true);
        Block top = new Block(0,-15,GameParameters.FIELDHEIGHT,15,true);
        Block bottom = new Block(0,GameParameters.FIELDHEIGHT-40,GameParameters.FIELDWIDTH,10,true);
        Block right = new Block(GameParameters.FIELDWIDTH,0,10,GameParameters.FIELDHEIGHT,true);
        blocks.add(left);
        blocks.add(right);
        blocks.add(top);
        blocks.add(bottom);
    }

    // CR: move from model
    void buildMap(){
        for (int i = 0; i < mapTemplate.length; i++){
            for (int j = 0; j < mapTemplate[i].length; j++){
                if (mapTemplate[i][j] == '0') continue;
                if (mapTemplate[i][j] == 't'){
                    Block trees = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,false,true);
                    blocks.add(trees);
                }
                if (mapTemplate[i][j] == '7'){
                    Block metal = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,true,false);
                    blocks.add(metal);
                }
                if (mapTemplate[i][j] == '1'){
                    Block brick = new Block(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,false,false);
                    blocks.add(brick);
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


    private void respawnPlayer() {
        mainPlayer.xPos = playerSpawnX;
        mainPlayer.yPos = playerSpawnY;
    }

    public static boolean isCollision(GamePrimitive o1, GamePrimitive o2){
        return o1.xPos < o2.xPos + o2.width &&
                o1.xPos + o1.width > o2.xPos &&
                o1.yPos < o2.yPos + o2.height &&
                o1.height + o1.yPos > o2.yPos;
    }

    private boolean moveTank(Tank tank, Direction direction){
        tank.move(direction,true);
        for (Iterator<Block> iterator = blocks.iterator(); iterator.hasNext();){
            Block block = iterator.next();
            if (block.isTransparent()) continue;
            if (isCollision(tank,block)){
                tank.move(Direction.getOpposite(direction),false);
                return false;
            }
        }
        for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext();){
            Tank enemytank = iterator.next();
            if (tank != enemytank && isCollision(tank,enemytank)){
                tank.move(Direction.getOpposite(direction),false);
                return false;
            }
        }
        if (tank != mainPlayer && isCollision(tank,mainPlayer)){
            tank.move(Direction.getOpposite(direction),false);
            return false;
        }
        return true;
    }

    public void moveMainPlayer(Direction direction){
        moveTank(mainPlayer,direction);
    }

    public void shootTank() {
        Bullet bullet = mainPlayer.shoot();
        if (bullet != null) bullets.add(bullet);
    }

    public GameObjects toGameObjects() {
        TankModel player = new TankModel(mainPlayer.getxPos(), mainPlayer.getyPos(), mainPlayer.getWidth(),
                mainPlayer.getHeight(),mainPlayer.getLastMove());
        Position bp = new Position(base.getxPos(), base.getyPos(), base.getWidth(), base.getHeight(),0);
        List<Position> bl = new ArrayList<>();
        for (Block block : blocks){
            if (block == base) continue;
            bl.add(blockIntoPosition(block));
        }
        List<TankModel> viewEnemies = new ArrayList<>();
        for (Tank enemy : enemies){
            viewEnemies.add(new TankModel(enemy.getxPos(),enemy.getyPos(),
                    enemy.getWidth(),enemy.getHeight(),enemy.getLastMove()));
        }
        List<Position> viewBullets = new ArrayList<>();
        for (Bullet bullet : bullets){
            viewBullets.add(new Position(bullet.getxPos(), bullet.getyPos(),
                    bullet.getWidth(),bullet.getHeight(),0));
        }

        return new GameObjects(player,bp,bl,viewEnemies,viewBullets);
    }

    private Position blockIntoPosition(Block block){
        // 1 - brick
        // 2 - metal
        // 3 - trees
        int type = 1;
        if (block.isTransparent()) type = 3;
        if (block.isInvincible()) type = 2;
        return new Position(block.getxPos(),block.getyPos(),
                block.getWidth(),block.getHeight(),type);
    }
}
