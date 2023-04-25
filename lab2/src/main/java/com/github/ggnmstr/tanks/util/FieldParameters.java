package com.github.ggnmstr.tanks.util;

public record FieldParameters(int mapNum, int enemiesLimit) {
    public static FieldParameters getDefaultParameters(){
        return new FieldParameters(1,10);
    }
}
