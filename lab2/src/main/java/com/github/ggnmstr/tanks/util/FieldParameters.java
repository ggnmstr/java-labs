package com.github.ggnmstr.tanks.util;

public record FieldParameters(String level, int enemiesLimit) {
    // CR: add game objects
    public static FieldParameters getDefaultParameters(){
        return new FieldParameters("level1",10);
    }
}
