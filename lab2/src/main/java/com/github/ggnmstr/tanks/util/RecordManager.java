package com.github.ggnmstr.tanks.util;

import java.util.ArrayList;
import java.util.List;

public class RecordManager {
    private final List<Score> highScores;
    private static final RecordManager manager = new RecordManager();
    private RecordManager(){
        highScores = new ArrayList<>();
    }
    public static final RecordManager getInstance(){
        return manager;
    }

    private RecordManager(List<Score> highScores) {
        this.highScores = highScores;
    }

    void addScore(String name, int score) {}

    List<Score> getHighScores() {
        return null;
    }

    void saveToFile() {

    }

    private List<Score> loadFromFile() {
        return null;
    }

}
