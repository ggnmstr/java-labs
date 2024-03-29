package com.github.ggnmstr.tanks.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.ggnmstr.tanks.util.Direction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record LevelObject(GameObjects gameObjects, List<EnemySpawnObject> enemySpawns, int fw, int fh, int bw, int bh, int tankSpeed, int playerHp, int enemyHp, int bulletSize, int nEnemies) {

    public static LevelObject fromFile(String fileName) {
        record Size(int width, int height) {}
        record Block(int x, int y, boolean isDestructible, boolean isTransparent) {}
        record Base(int x, int y, Size size) {}
        record Player(int x, int y, Size size) {}
        record EnemySpawn(int x, int y) {}
        record Bullet(int x, int y, Direction direction) {}
        record Level(Size size, int playerHp, int enemyHp, int enemiesCount, List<EnemySpawn> enemySpawns,
                     Size blockSize, List<Block> blocks, Base base, Player player, List<Bullet> bullets) {}

        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        Level level;
        try {
            InputStream config = LevelObject.class.getResourceAsStream("/maps/" + fileName);
            level = objectMapper.readValue(config, Level.class);
        } catch (Throwable e) {
            // TODO: handle
            throw new RuntimeException(e);
        }

        Player player = level.player;
        Size fieldSize = level.size;
        int tankSpeed = fieldSize.height / 204;
        int bulletSize = fieldSize.height / 83;
        Size blockSize = level.blockSize;
        Base base = level.base;
        List<Block> blocks = level.blocks();
        List<Bullet> bullets = level.bullets;

        TankObject mainPlayer = new TankObject(player.x, player.y, player.size.width, player.size.height, Direction.NONE);
        BlockObject baseObject = new BlockObject(base.x, base.y, base.size.width, base.size.height, false,false);

        List<BlockObject> blockObjects = new ArrayList<>();
        for (Block block : blocks) {
            blockObjects.add(new BlockObject(block.x, block.y, blockSize.width, blockSize.height, block.isDestructible,block.isTransparent));
        }

        List<EnemySpawnObject> enemySpawnObjects = new ArrayList<>();
        for (EnemySpawn enemySpawn : level.enemySpawns) {
            enemySpawnObjects.add(new EnemySpawnObject(enemySpawn.x, enemySpawn.y, player.size().width()));
        }

        List<BulletObject> bulletObjects = new ArrayList<>();
        for (Bullet bullet : bullets){
            bulletObjects.add(new BulletObject(bullet.x,bullet.y,
                    bulletSize, bulletSize, bullet.direction));
        }

        GameObjects gameObjects = new GameObjects(mainPlayer, baseObject,
                blockObjects, Collections.emptyList(), bulletObjects);

        return new LevelObject(gameObjects, enemySpawnObjects, fieldSize.width, fieldSize.height, blockSize.width, blockSize.height, tankSpeed, level.playerHp, level.enemyHp, bulletSize, level.enemiesCount);
    }
}
