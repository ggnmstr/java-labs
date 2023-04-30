package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {

    /*

      CR:  t->b

     */

    @Test
    void destroyBaseTest(){
        BattleField field = new BattleField(FieldParameters.getDefaultParameters());
        field.resetField();
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.updateField();



        for (int i = 0; i < 300; i++){
            field.updateField();
        }
        field.shootTank();
        for (int i = 0; i < 300; i++){
            field.updateField();
        }
        field.shootTank();
        NullPointerException exception = assertThrows(NullPointerException.class,()->{
            for (int i = 0; i < 300; i++){
                field.updateField();
            }
        },"abc");
        assertTrue(exception.getMessage().contains("gameLost(int)"));
        GameObjects gameObjects = field.toGameObjects();
        // CR: check that base is destroyed
    }

//    private static class GameLostFieldListener implements FieldListener {
//
//        private int invokedGameLost;
//
//        @Override
//        public void gameLost(int score) {
//            invokedGameLost++;
//        }
//
//        @Override
//        public void gameWon(int score) {
//
//        }
//
//        @Override
//        public void updateStats(int hp, int enemiesleft, int score) {
//
//        }
//    }
}
