package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.*;
import com.github.ggnmstr.tanks.util.Direction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BattleField {

    private final Tank mainPlayer;
    private final FieldBlock playerSpawn;

    private final List<Tank> enemies = new ArrayList<>();
    private final List<Spawn> enemySpawns = new ArrayList<>();

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Bullet> bulletsToRemove = new ArrayList<>();

    private final List<FieldBlock> fieldBlocks = new ArrayList<>();
    private final FieldBlock base;

    private final LevelObject initialConfig;

    private FieldListener fieldListener;

    private int enemiesLeft;
    private int score = 0;

    public BattleField(LevelObject levelObject) {
        GameObjects gameObjects = levelObject.gameObjects();
        TankObject mainPlayerObject = gameObjects.mainPlayer();

        this.mainPlayer = Tank.from(mainPlayerObject, levelObject.playerHp());
        this.playerSpawn = new FieldBlock(mainPlayerObject.x(), mainPlayerObject.y(), 0, 0, false, false);

        this.base = new FieldBlock(gameObjects.base().x(), gameObjects.base().y(),
                gameObjects.base().width(), gameObjects.base().height(), gameObjects.base().isDestructible(), false);

        gameObjects.enemies().stream().map(e -> Tank.from(e, levelObject.enemyHp())).forEach(enemies::add);
        levelObject.enemySpawns().stream().map(Spawn::from).forEach(enemySpawns::add);

        gameObjects.blocks().stream().map(FieldBlock::from).forEach(fieldBlocks::add);
        fieldBlocks.add(base);
        this.enemiesLeft = levelObject.nEnemies();
        this.initialConfig = levelObject;

        generateBorders();
        gameObjects.bullets().stream().map(Bullet::from).forEach(bullets::add);
    }


    public void updateField() {
        moveBullets();
        moveEnemyTanks();
    }

    private Direction preferedDirection(Tank tank){
        Direction last = tank.getLastMove();
        Direction res;
        int dx = base.xPos - tank.xPos;
        int dy = base.yPos - tank.yPos;
        if (dx > 0 && dy > 0){
            res = Direction.RIGHT;
            if (last == Direction.RIGHT) res = Direction.DOWN;
        } else if (dx <= 0 && dy > 0) {
            res = last == Direction.DOWN ? Direction.LEFT : Direction.DOWN;
        } else {
            res = Direction.UP;
        }
        int c = ThreadLocalRandom.current().nextInt(0,10);
        if (c % 3 == 0) res = Direction.opposite(res);
        return res;
    }

    private void moveEnemyTanks() {
        for (Tank enemyTank : enemies) {
            Direction dir = enemyTank.getLastMove();
            // CR: smart strategy for enemies
            if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                //if (enemyTank.getyPos() >= mainPlayer.getyPos()-50 && enemyTank.getyPos() <= mainPlayer.getyPos() + 50){
                Bullet bullet = enemyTank.shoot();
                if (bullet != null) bullets.add(bullet);
            }

            if (moveTank(enemyTank, dir)) continue;
            else dir = preferedDirection(enemyTank);
            moveTank(enemyTank, dir);
        }
    }

    private void moveBullets() {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bulletHasCollision(bullet)) {
                Tank owner = bullet.getOwner();
                if (owner != null) owner.makeShootable();
                iterator.remove();
            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    private boolean bulletHasCollision(Bullet bullet) {
        boolean flag = false;
        boolean explode = false;
        for (Iterator<FieldBlock> iterator = fieldBlocks.iterator(); iterator.hasNext(); ) {
            FieldBlock fieldBlock = iterator.next();
            if (fieldBlock.isTransparent()) continue;
            if (isCollision(bullet, fieldBlock)) {
                if (fieldBlock == base) {
                    fieldListener.gameLost(score);
                    return true;
                }
                if (fieldBlock.isDestructible()) {
                    explode = true;
                    iterator.remove();
                    break;
                }
                flag = true;
            }
        }
        if (explode){
            Bullet explosion = Bullet.explosionFrom(bullet);
            triggerExplosion(explosion);
            return true;
        }
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet otherbullet = iterator.next();
            if (otherbullet != bullet && isCollision(bullet, otherbullet)) {
                Tank otherowner = otherbullet.getOwner();
                if (otherowner != null) otherowner.makeShootable();
                bulletsToRemove.add(otherbullet);
                return true;
            }
        }
        if (bullet.getOwner() == mainPlayer) {
            for (Iterator<Tank> iterator = enemies.iterator(); iterator.hasNext(); ) {
                Tank enemy = iterator.next();
                if (isCollision(bullet, enemy)) {
                    score += 100;
                    fieldListener.updateStats(mainPlayer.getHP(), enemiesLeft, score);
                    iterator.remove();
                    if (enemiesLeft == 0 && enemies.isEmpty()) {
                        fieldListener.gameWon(score);
                    }
                    return true;
                }
            }
        }

        if (isCollision(bullet, mainPlayer)) {
            if (!mainPlayer.takeDamage()) fieldListener.gameLost(score);
            fieldListener.updateStats(mainPlayer.getHP(), enemiesLeft, score);
            respawnPlayer();
            return true;
        }
        return flag;
    }

    private void triggerExplosion(Bullet explosion){
        for (Iterator<FieldBlock> iterator = fieldBlocks.iterator(); iterator.hasNext(); ) {
            FieldBlock fieldBlock = iterator.next();
            if (fieldBlock.isTransparent()) continue;
            if (fieldBlock != base && isCollision(explosion, fieldBlock)) {
                if (fieldBlock.isDestructible()) {
                    iterator.remove();
                }
            }
        }
    }

    public void setFieldListener(FieldListener fieldListener) {
        this.fieldListener = fieldListener;
    }

    public void spawnEnemy() {
        if (enemiesLeft <= 0) return;
        int n = enemySpawns.size();
        Spawn spawn = enemySpawns.get(ThreadLocalRandom.current().nextInt(0, n));
        Tank newEnemy = null;
        if (!pointOccupied(spawn)) newEnemy = new Tank(spawn.xPos, spawn.yPos, 1);
        if (newEnemy == null) return;
        enemiesLeft--;
        fieldListener.updateStats(mainPlayer.getHP(), enemiesLeft, score);
        enemies.add(newEnemy);
    }

    private boolean pointOccupied(Spawn point) {
        if (isCollision(mainPlayer, point)) return true;
        for (Tank enemy : enemies) {
            if (isCollision(enemy, point)) return true;
        }
        return false;
    }

    void generateBorders() {
        FieldBlock left = new FieldBlock(-15, 0, 15, GameParameters.FIELDWIDTH, true, false);
        FieldBlock top = new FieldBlock(0, -15, GameParameters.FIELDHEIGHT, 15, true, false);
        FieldBlock bottom = new FieldBlock(0, GameParameters.FIELDHEIGHT - 40, GameParameters.FIELDWIDTH, 10, true, false);
        FieldBlock right = new FieldBlock(GameParameters.FIELDWIDTH, 0, 10, GameParameters.FIELDHEIGHT, true, false);
        fieldBlocks.add(left);
        fieldBlocks.add(right);
        fieldBlocks.add(top);
        fieldBlocks.add(bottom);
    }

    private void respawnPlayer() {
        mainPlayer.xPos = playerSpawn.xPos;
        mainPlayer.yPos = playerSpawn.yPos;
    }

    private boolean moveTank(Tank tank, Direction direction) {
        tank.move(direction, true);
        for (FieldBlock fieldBlock : fieldBlocks) {
            if (fieldBlock.isTransparent()) continue;
            if (isCollision(tank, fieldBlock)) {
                tank.move(Direction.opposite(direction), false);
                return false;
            }
        }
        for (Tank enemytank : enemies) {
            if (tank != enemytank && isCollision(tank, enemytank)) {
                tank.move(Direction.opposite(direction), false);
                return false;
            }
        }
        if (tank != mainPlayer && isCollision(tank, mainPlayer)) {
            tank.move(Direction.opposite(direction), false);
            return false;
        }
        return true;
    }

    public void moveMainPlayer(Direction direction) {
        moveTank(mainPlayer, direction);
    }

    public void shootTank() {
        Bullet bullet = mainPlayer.shoot();
        if (bullet != null) bullets.add(bullet);
    }

    public GameObjects toGameObjects() {
        TankObject player = new TankObject(mainPlayer.getxPos(), mainPlayer.getyPos(), mainPlayer.getWidth(),
                mainPlayer.getHeight(), mainPlayer.getLastMove());
        BlockObject bp = new BlockObject(base.getxPos(), base.getyPos(), base.getWidth(), base.getHeight(), false,false);
        List<BlockObject> bl = new ArrayList<>();
        for (FieldBlock fieldBlock : fieldBlocks) {
            if (fieldBlock == base) continue;
            bl.add(new BlockObject(fieldBlock.xPos, fieldBlock.yPos, fieldBlock.width, fieldBlock.height, fieldBlock.isDestructible(),fieldBlock.isTransparent()));
        }
        List<TankObject> viewEnemies = new ArrayList<>();
        for (Tank enemy : enemies) {
            viewEnemies.add(new TankObject(enemy.getxPos(), enemy.getyPos(),
                    enemy.getWidth(), enemy.getHeight(), enemy.getLastMove()));
        }
        List<BulletObject> viewBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {
            viewBullets.add(new BulletObject(bullet.getxPos(), bullet.getyPos(),
                    bullet.getWidth(), bullet.getHeight(),bullet.getDirection()));
        }

        return new GameObjects(player, bp, bl, viewEnemies, viewBullets);
    }

    public LevelObject initialConfig() {
        return initialConfig;
    }

    private static boolean isCollision(GamePrimitive o1, GamePrimitive o2) {
        return o1.xPos < o2.xPos + o2.width &&
                o1.xPos + o1.width > o2.xPos &&
                o1.yPos < o2.yPos + o2.height &&
                o1.height + o1.yPos > o2.yPos;
    }

}
