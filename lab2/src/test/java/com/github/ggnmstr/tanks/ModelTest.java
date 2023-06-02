package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.dto.*;
import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/*

- player destroys base +
- enemy destroys base
- enemy kills player +
- player kills enemy
- enemy shoots enemy
- player shoots indestructible block +
- enemy shoots indestructible block
- -||- destructible block
- player moves into boundary +
- player moves into transparent boundary +
- enemy moves into boundary
- player kills all enemies
- player moves into enemy
- enemy moves into enemy
- player spawns on enemy +
- enemy spawns on enemy
- enemy spawns on player
- bullets collapse with each other +

(do not move into walls)
____
| e |
| b |
| e |
____

 */
public class ModelTest {
    @Test
    void destroyBaseTest() {
        // Player tank is spawned on the left side of the base
        // Then tank turns right and shoots - base should be destroyed and game lost
        // CR: initialize level object manually
        GameObjects gameObjects = new GameObjects(
                new TankObject(300, 690, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 1);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.updateField();

        assertEquals(1, listener.getInvokedGameLost());
    }

    @Test
    void baseNotDestroyedTest() {
        // Same situation, but tank does not turn right - so base shouldn't be destroyed.
        GameObjects gameObjects = new GameObjects(
                new TankObject(300, 690, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 1);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.shootTank();
        field.updateField();

        assertEquals(0, listener.getInvokedGameLost());
    }

    @Test
    void bulletsDestroyedTest() {
        // 2 bullets spawned right against each other, so only 1 update is required to destroy both
        GameObjects gameObjects = new GameObjects(
                new TankObject(300, 690, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>() {{
                    add(new BulletObject(100, 10, 10, 10, Direction.DOWN));
                    add(new BulletObject(100, 20, 10, 10, Direction.UP));
                }}
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 1);
        BattleField field = new BattleField(levelObject);
        assertEquals(2, field.toGameObjects().bullets().size());
        field.updateField();
        assertTrue(field.toGameObjects().bullets().isEmpty());
    }

    @Test
    void loseByDyingTest() {
        // Tank with 1 hp left is spawned against bullet
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 20, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>() {{
                    add(new BulletObject(100, 10, 10, 10, Direction.DOWN));
                }}
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 1);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.updateField();
        assertEquals(1, listener.getInvokedGameLost());
    }

    @Test
    void killEnemyTest() {
        // Enemy is right above player tank, then player shoots
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 300, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(){{
                    add(new TankObject(100,360,60,60,Direction.DOWN));
                }},
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 10);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        assertEquals(1, field.toGameObjects().enemies().size());
        field.shootTank();
        field.updateField();
        assertTrue(field.toGameObjects().enemies().isEmpty());
    }

    @Test
    void winByKillingEveryoneTest() {
        // Enemy is right above player tank, then player shoots
        // but 0 enemies left so that's a winning scenario
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 300, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(){{
                    add(new TankObject(100,360,60,60,Direction.DOWN));
                }},
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 0);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.shootTank();
        field.updateField();
        assertEquals(1, listener.getInvokedGameWon());
    }

    @Test
    void bulletExplosionTest() {
        // Player tank is spawned against the wall of 4 blocks
        // After 1 shot all of them should be destroyed due to explosion
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 285, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(){{
                    add(new BlockObject(100,360,15,15,true,false));
                    add(new BlockObject(115,360,15,15,true,false));
                    add(new BlockObject(130,360,15,15,true,false));
                    add(new BlockObject(145,360,15,15,true,false));

                }},
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 0);
        BattleField field = new BattleField(levelObject);
        assertEquals(8, field.toGameObjects().blocks().size());
        field.shootTank();
        field.updateField();
        // 4 blocks left - borders of map
        assertEquals(4, field.toGameObjects().blocks().size());
    }

    @Test
    void unbreakableWallTest() {
        // Player tank is spawned against the UNBREAKABLE wall of 4 blocks
        // After 1 shot and update bullet should be destroyed, blocks should remain
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 285, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(){{
                    add(new BlockObject(100,360,15,15,false,false));
                    add(new BlockObject(115,360,15,15,false,false));
                    add(new BlockObject(130,360,15,15,false,false));
                    add(new BlockObject(145,360,15,15,false,false));

                }},
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 0);
        BattleField field = new BattleField(levelObject);
        field.shootTank();
        field.updateField();
        // 4+4=8 blocks left - borders of map + 4 unbreakable blocks
        assertEquals(8, field.toGameObjects().blocks().size());
    }

    @Test
    void stumbleUponWallTest() {
        // Player tank is spawned under the wall of 4 blocks
        // So it should NOT be available to move up (change coordinates)
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 300, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(){{
                    add(new BlockObject(100,360,15,15,true,false));
                    add(new BlockObject(115,360,15,15,true,false));
                    add(new BlockObject(130,360,15,15,true,false));
                    add(new BlockObject(145,360,15,15,true,false));

                }},
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 0);
        BattleField field = new BattleField(levelObject);
        int by = field.toGameObjects().mainPlayer().y();
        field.moveMainPlayer(Direction.DOWN);
        int ay = field.toGameObjects().mainPlayer().y();
        assertEquals(by, ay);
    }

    @Test
    void walkThroughTransparentBlocksTest() {
        // Player tank is spawned above the wall of 4 transparent blocks
        // So it should be available to move up (change coordinates)
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 300, 60, 60, Direction.DOWN),
                new BlockObject(360, 705, 60, 60, true, false),
                new ArrayList<>(){{
                    add(new BlockObject(100,360,15,15,true,true));
                    add(new BlockObject(115,360,15,15,true,true));
                    add(new BlockObject(130,360,15,15,true,true));
                    add(new BlockObject(145,360,15,15,true,true));

                }},
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects, new ArrayList<>(), 832, 832, 1, 1, 0);
        BattleField field = new BattleField(levelObject);
        int by = field.toGameObjects().mainPlayer().y();
        field.moveMainPlayer(Direction.DOWN);
        int ay = field.toGameObjects().mainPlayer().y();
        assertNotEquals(ay, by);
    }

    @Test
    void spawnOccupiedTest(){
        // Player and enemy spawn have the same coordinates => spawning an enemy should not do anything
        GameObjects gameObjects = new GameObjects(
                new TankObject(100, 300, 60, 60, Direction.DOWN),
                new BlockObject(0, 0, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects,
                new ArrayList<>(){{
                    add(new EnemySpawnObject(100,300));
                }},
                832, 832, 1, 1, 10);
        BattleField field = new BattleField(levelObject);
        field.spawnEnemy();
        assertTrue(field.toGameObjects().enemies().isEmpty());
    }

    @Test
    void spawnNotOccupiedTest(){
        // Spawn position not occupied => spawning should work
        GameObjects gameObjects = new GameObjects(
                new TankObject(400, 400, 60, 60, Direction.DOWN),
                new BlockObject(0, 0, 60, 60, true, false),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        LevelObject levelObject = new LevelObject(gameObjects,
                new ArrayList<>(){{
                    add(new EnemySpawnObject(100,100));
                }},
                832, 832, 1, 1, 10);
        BattleField field = new BattleField(levelObject);
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.spawnEnemy();
        assertEquals(1,field.toGameObjects().enemies().size());
    }

    private static class TestFieldListener implements FieldListener {

        private int invokedGameLost;
        private int invokedGameWon;

        public int getInvokedGameLost() {
            return invokedGameLost;
        }

        public int getInvokedGameWon() {
            return invokedGameWon;
        }

        @Override
        public void gameLost(int score) {
            invokedGameLost++;
        }

        @Override
        public void gameWon(int score) {
            invokedGameWon++;
        }

        @Override
        public void updateStats(int hp, int enemiesleft, int score) {

        }
    }
}
