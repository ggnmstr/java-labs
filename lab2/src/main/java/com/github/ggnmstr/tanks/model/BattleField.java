package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.*;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import com.github.ggnmstr.tanks.util.MapTemplateCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class BattleField {

    private FieldBlock base;

    private FieldListener fieldListener;

    private Tank mainPlayer;
    private final List<Tank> enemies = new ArrayList<>();

    public final List<EnemySpawnPoint> enemySpawnPoints = new ArrayList<>();

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<FieldBlock> fieldBlocks = new ArrayList<>();

    private final List<Bullet> bulletsToRemove = new ArrayList<>();

    private int playerSpawnX;
    private int playerSpawnY;

    private int enemiesLeft;

    private int score = 0;

    private final char[][] mapTemplate;

    // CR: accept all game objects, initialize itself
    public BattleField(FieldParameters parameters){
        this.enemiesLeft = parameters.enemiesLimit();
        mapTemplate = MapTemplateCreator.create(parameters.level());
    }

    public void updateField(){
        moveBullets();
        moveEnemyTanks();
    }

    private void moveEnemyTanks() {
        for (Tank enemyTank : enemies){
            Direction dir = enemyTank.getLastMove();
            // CR: smart strategy for enemies
            if (ThreadLocalRandom.current().nextInt(0,2) == 0){
            //if (enemyTank.getyPos() >= mainPlayer.getyPos()-50 && enemyTank.getyPos() <= mainPlayer.getyPos() + 50){
                Bullet bullet = enemyTank.shoot();
                if (bullet != null) bullets.add(bullet);
            }

            if (moveTank(enemyTank,dir)) continue;
            // CR: ???
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
        for (Iterator<FieldBlock> iterator = fieldBlocks.iterator(); iterator.hasNext(); ) {
            FieldBlock fieldBlock = iterator.next();
            if (fieldBlock.isTransparent()) continue;
            if (isCollision(bullet, fieldBlock)) {
                if (fieldBlock == base){
                    fieldListener.gameLost(score);
                    return true;
                }
                if (!fieldBlock.isInvincible()) {
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

    public void resetField(){
        // CR: restore initial config
        enemiesLeft = 10;
        clearMap();
        buildMap();
        score = 0;
        mainPlayer = new Tank(playerSpawnX,playerSpawnY,GameParameters.PLAYERHP);
        generateBorders();
        //spawnEnemy();
        //fieldListener.updateStats(mainPlayer.getHP(),enemiesLeft,value);
    }

    private void clearMap() {
        fieldBlocks.clear();
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
        if (!pointOccupied(point)) newEnemy = point.spawnTank();
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
        FieldBlock left = new FieldBlock(-15,0,15,GameParameters.FIELDWIDTH,true);
        FieldBlock top = new FieldBlock(0,-15,GameParameters.FIELDHEIGHT,15,true);
        FieldBlock bottom = new FieldBlock(0,GameParameters.FIELDHEIGHT-40,GameParameters.FIELDWIDTH,10,true);
        FieldBlock right = new FieldBlock(GameParameters.FIELDWIDTH,0,10,GameParameters.FIELDHEIGHT,true);
        fieldBlocks.add(left);
        fieldBlocks.add(right);
        fieldBlocks.add(top);
        fieldBlocks.add(bottom);
    }

    // CR: move from model
    void buildMap(){
        for (int i = 0; i < mapTemplate.length; i++){
            for (int j = 0; j < mapTemplate[i].length; j++){
                if (mapTemplate[i][j] == '0') continue;
                if (mapTemplate[i][j] == 't'){
                    FieldBlock trees = new FieldBlock(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,false,true);
                    fieldBlocks.add(trees);
                }
                if (mapTemplate[i][j] == '7'){
                    FieldBlock metal = new FieldBlock(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,true,false);
                    fieldBlocks.add(metal);
                }
                if (mapTemplate[i][j] == '1'){
                    FieldBlock brick = new FieldBlock(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,false,false);
                    fieldBlocks.add(brick);
                }
                if (mapTemplate[i][j] == '2'){
                    EnemySpawnPoint spawnPoint = new EnemySpawnPoint(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT);
                    enemySpawnPoints.add(spawnPoint);
                }
                if (mapTemplate[i][j] == '3'){
                    base = new FieldBlock(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,
                            GameParameters.BASEWIDTH,GameParameters.BASEHEIGHT,false);
                    fieldBlocks.add(base);
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
        //  CR: Position position = tank.positionAfter(direction);
        tank.move(direction,true);
        for (Iterator<FieldBlock> iterator = fieldBlocks.iterator(); iterator.hasNext();){
            FieldBlock fieldBlock = iterator.next();
            if (fieldBlock.isTransparent()) continue;
            if (isCollision(tank, fieldBlock)){
                tank.move(Direction.opposite(direction),false);
                return false;
            }
        }
        for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext();){
            Tank enemytank = iterator.next();
            if (tank != enemytank && isCollision(tank,enemytank)){
                tank.move(Direction.opposite(direction),false);
                return false;
            }
        }
        if (tank != mainPlayer && isCollision(tank,mainPlayer)){
            tank.move(Direction.opposite(direction),false);
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
        Position bp = new Position(base.getxPos(), base.getyPos(), base.getWidth(), base.getHeight());
        List<BlockTO> bl = new ArrayList<>();
        for (FieldBlock fieldBlock : fieldBlocks){
            if (fieldBlock == base) continue;
            bl.add(blockIntoBlockTO(fieldBlock));
        }
        List<TankModel> viewEnemies = new ArrayList<>();
        for (Tank enemy : enemies){
            viewEnemies.add(new TankModel(enemy.getxPos(),enemy.getyPos(),
                    enemy.getWidth(),enemy.getHeight(),enemy.getLastMove()));
        }
        List<Position> viewBullets = new ArrayList<>();
        for (Bullet bullet : bullets){
            viewBullets.add(new Position(bullet.getxPos(), bullet.getyPos(),
                    bullet.getWidth(),bullet.getHeight()));
        }

        return new GameObjects(player,bp,bl,viewEnemies,viewBullets);
    }

    private BlockTO blockIntoBlockTO(FieldBlock fieldBlock){
        BlockType type = BlockType.BRICK;
        Position position = new Position(
                fieldBlock.getxPos(), fieldBlock.getyPos(),
                fieldBlock.getWidth(),fieldBlock.getHeight()
        );
        if (fieldBlock.isTransparent()) type = BlockType.TREES;
        if (fieldBlock.isInvincible()) type = BlockType.METAL;
        return new BlockTO(position,type);
    }
}
