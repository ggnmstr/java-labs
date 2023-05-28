package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.dto.BlockObject;
import com.github.ggnmstr.tanks.dto.LevelObject;
import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {
    @Test
    void destroyBaseTest(){
        // Player tank is spawned on the left side of the base
        // Then tank turns right and shoots - base should be destroyed and game lost
        BattleField field = new BattleField(LevelObject.fromFile("test/test1.json"));
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.updateField();

        assertEquals(1,listener.getInvokedGameLost());
    }

    @Test
    void baseNotDestroyedTest(){
        // Same situation, but tank does not turn right - so base shouldn't be destroyed.
        BattleField field = new BattleField(LevelObject.fromFile("test/test1.json"));
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.shootTank();
        field.updateField();

        assertEquals(0,listener.getInvokedGameLost());
    }

    @Test
    void bulletsDestroyedTest(){
        // 2 bullets spawned right against each other, so only 1 update is required to destroy both
        BattleField field = new BattleField(LevelObject.fromFile("test/bulletsDestroyedTest.json"));
        assertEquals(2,field.toGameObjects().bullets().size());
        field.updateField();
        assertTrue(field.toGameObjects().bullets().isEmpty());
    }

    @Test
    void loseByDyingTest(){
        // Tank with 1 hp left is spawned against bullet
        BattleField field = new BattleField(LevelObject.fromFile("test/loseByDyingTest.json"));
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.updateField();
        assertEquals(1,listener.getInvokedGameLost());
    }

    @Test
    void killEnemyTest(){
        // Enemy is spawned right above player tank, then player shoots
        BattleField field = new BattleField(LevelObject.fromFile("test/killEnemyTest.json"));
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.spawnEnemy();
        assertEquals(1,field.toGameObjects().enemies().size());
        field.shootTank();
        field.updateField();
        assertTrue(field.toGameObjects().enemies().isEmpty());
    }

    @Test
    void winByKillingEveryoneTest(){
        // Enemy is spawned right above player tank, then player shoots
        // but enemyLimit = 1 so that's a winning scenario
        BattleField field = new BattleField(LevelObject.fromFile("test/killEveryoneTest.json"));
        TestFieldListener listener = new TestFieldListener();
        field.setFieldListener(listener);
        field.spawnEnemy();
        field.shootTank();
        field.updateField();
        assertEquals(1,listener.getInvokedGameWon());
    }

    @Test
    void bulletExplosionTest(){
        // Player tank is spawned against the wall of 4 blocks
        // After 1 shot all of them should be destroyed due to explosion
        BattleField field = new BattleField(LevelObject.fromFile("test/bulletExplosionTest.json"));
        assertEquals(8, field.toGameObjects().blocks().size());
        field.shootTank();
        field.updateField();
        // 4 blocks left - borders of map
        assertEquals(4,field.toGameObjects().blocks().size());
    }

    @Test
    void unbreakableWallTest(){
        // Player tank is spawned against the UNBREAKABLE wall of 4 blocks
        // After 1 shot and update bullet should be destroyed, blocks should remanin
        BattleField field = new BattleField(LevelObject.fromFile("test/unbreakableWallTest.json"));
        field.shootTank();
        field.updateField();
        // 4+4=8 blocks left - borders of map + 4 unbreakable blocks
        assertEquals(8,field.toGameObjects().blocks().size());
    }

    @Test
    void stumbleUponWallTest(){
        // Player tank is spawned under the wall of 4 blocks
        // So it should NOT be available to move up (change coordinates)
        BattleField field = new BattleField(LevelObject.fromFile("test/stumbleUponWallTest.json"));
        int by = field.toGameObjects().mainPlayer().y();
        field.moveMainPlayer(Direction.DOWN);
        int ay = field.toGameObjects().mainPlayer().y();
        assertEquals(by,ay);
    }

    @Test
    void walkThroughTranparentBlocksTest(){
        // Player tank is spawned above the wall of 4 transparent blocks
        // So it should be available to move up (change coordinates)
        BattleField field = new BattleField(LevelObject.fromFile("test/transparentWallTest.json"));
        int by = field.toGameObjects().mainPlayer().y();
        field.moveMainPlayer(Direction.DOWN);
        int ay = field.toGameObjects().mainPlayer().y();
        assertNotEquals(ay,by);
    }


    private static class TestFieldListener implements FieldListener {

        private int invokedGameLost;
        private int invokedGameWon;

        public int getInvokedGameLost(){
            return invokedGameLost;
        }

        public int getInvokedGameWon(){
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
