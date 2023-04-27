package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ModelTest {

    @Test
    void destroyBaseTest(){
        BattleField field = new BattleField(FieldParameters.getDefaultParameters());
        field.initField();
        field.moveMainPlayer(Direction.RIGHT);
        for (int i = 0; i < 300; i++){
            field.updateField();
        }
        field.shootTank();
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
        assertTrue(exception.getMessage().contains("\"this.fieldListener\" is null"));
    }
}
