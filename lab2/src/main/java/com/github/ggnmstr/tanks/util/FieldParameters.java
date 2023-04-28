package com.github.ggnmstr.tanks.util;

public record FieldParameters(int mapNum, int enemiesLimit) {
    // CR: add game objects
    public static FieldParameters getDefaultParameters(){
        return new FieldParameters(1,10);
    }
}
