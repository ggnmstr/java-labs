package com.github.ggnmstr.tanks.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.ggnmstr.tanks.util.Direction;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record LevelObject(GameObjects gameObjects, List<EnemySpawnObject> enemySpawns, int width, int height, int playerHp, int enemyHp, int nEnemies) {

    public static LevelObject fromFile(String fileName) {
        record Size(int width, int height) {}
        record Block(int x, int y, boolean isDestructible, boolean isTransparent) {}
        record Base(int x, int y, Size size) {}
        record Player(int x, int y, Size size) {}
        record EnemySpawn(int x, int y) {}
        record Level(Size size, int playerHp, int enemyHp, int enemiesCount, List<EnemySpawn> enemySpawns, Size blockSize, List<Block> blocks, Base base, Player player) {}

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
        Size blockSize = level.blockSize;
        Base base = level.base;
        List<Block> blocks = level.blocks();

        TankObject mainPlayer = new TankObject(player.x, player.y, player.size.width, player.size.height, Direction.NONE);
        BlockObject baseObject = new BlockObject(base.x, base.y, base.size.width, base.size.height, false,false);
        List<BlockObject> blockObjects = new ArrayList<>();

        for (Block block : blocks) {
            blockObjects.add(new BlockObject(block.x, block.y, blockSize.width, blockSize.height, block.isDestructible,block.isTransparent));
        }

        List<EnemySpawnObject> enemySpawnObjects = new ArrayList<>();
        for (EnemySpawn enemySpawn : level.enemySpawns) {
            enemySpawnObjects.add(new EnemySpawnObject(enemySpawn.x, enemySpawn.y));
        }

        GameObjects gameObjects = new GameObjects(mainPlayer, baseObject,
                blockObjects, Collections.emptyList(), Collections.emptyList());

        return new LevelObject(gameObjects, enemySpawnObjects, fieldSize.width, fieldSize.height, level.playerHp, level.enemyHp, level.enemiesCount);
    }
}
