package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.FieldParameters;
import org.junit.jupiter.api.Test;


public class ModelTest {

    @Test
    void destroyBaseTest(){
        BattleField field = new BattleField(FieldParameters.getDefaultParameters());
        field.initField();
        field.moveMainPlayer(Direction.RIGHT);
        field.shootTank();
        field.shootTank();
        field.shootTank();

    }
}
