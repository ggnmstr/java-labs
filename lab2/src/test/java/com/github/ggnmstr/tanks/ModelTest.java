package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.dto.LevelObject;
import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
//import com.github.ggnmstr.tanks.util.FieldParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {
    @Test
    void destroyBaseTest(){
        BattleField field = new BattleField(LevelObject.fromFile("test/test1.json"));
        GameLostFieldListener listener = new GameLostFieldListener();
        field.setFieldListener(listener);
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.updateField();

        assertEquals(1,listener.getInvokedGameLost());
    }

    @Test
    void baseNotDestroyedTest(){
        BattleField field = new BattleField(LevelObject.fromFile("test/test1.json"));
        GameLostFieldListener listener = new GameLostFieldListener();
        field.setFieldListener(listener);
        field.shootTank();
        field.updateField();

        assertEquals(0,listener.getInvokedGameLost());
    }

    @Test
    void bulletsDestroyedTest(){
        // 2 bullets spawned right against each other here, so only 1 update required to destroy both
        BattleField field = new BattleField(LevelObject.fromFile("test/test2.json"));
        field.updateField();
        assertTrue(field.toGameObjects().bullets().isEmpty());
    }


    private static class GameLostFieldListener implements FieldListener {

        private int invokedGameLost;

        public int getInvokedGameLost(){
            return invokedGameLost;
        }

        @Override
        public void gameLost(int score) {
            invokedGameLost++;
        }

        @Override
        public void gameWon(int score) {

        }

        @Override
        public void updateStats(int hp, int enemiesleft, int score) {

        }
    }
}
