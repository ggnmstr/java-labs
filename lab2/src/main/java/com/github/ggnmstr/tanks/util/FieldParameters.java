package com.github.ggnmstr.tanks.util;

import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.model.MapInfo;

public record FieldParameters(String level, int enemiesLimit) {
//public record FieldParameters(GameObjects gameObjects, MapInfo mapInfo) {
    // CR: add game objects
    // CR: do we need it?
    public static FieldParameters getDefaultParameters(){
        return new FieldParameters("level1",10);
    }
}
