package com.github.ggnmstr.tanks.util;

public record FieldParameters(String level, int enemiesLimit) {
    // CR: add game objects
    // CR: do we need it?
    public static FieldParameters getDefaultParameters(){
        return new FieldParameters("level1",10);
    }
}
