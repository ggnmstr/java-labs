package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.model.FieldListener;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {
    @Test
    void destroyBaseTest(){
        BattleField field = new BattleField(new FieldParameters("test/test1",0));
        GameLostFieldListener listener = new GameLostFieldListener();
        field.setFieldListener(listener);
        field.resetField();
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.updateField();

        assertEquals(1,listener.getInvokedGameLost());
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
