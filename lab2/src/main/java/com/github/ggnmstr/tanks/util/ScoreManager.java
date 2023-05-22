package com.github.ggnmstr.tanks.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    private final List<Score> highScores = new ArrayList<>();

    private static ScoreManager instance;

    private ScoreManager(){
        loadFromFile();
    }

    public static ScoreManager getInstance(){
        if (instance == null){
            instance = new ScoreManager();
        }
        return instance;
    }

    public void addScore(String name, int score) {
        int i = 0;
        for (Score curscore : highScores) {
            if (score > curscore.value()) {
                break;
            }
            i++;
        }
        if (i <= 9) {
            Score newscore = new Score(name,score);
            highScores.add(i,newscore);
            if (highScores.size() >= 11) highScores.remove(10);
        }
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    public void saveToFile() {
        Path filePath = Path.of("scores.txt");
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            for (Score entry : highScores) {
                String line = entry.playerName() + " : " + entry.value() + "\n";
                Files.writeString(filePath, line, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        Path filePath = Path.of("scores.txt");
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(" : ");
                String playerName = parts[0];
                int value = Integer.parseInt(parts[1]);
                Score score = new Score(playerName, value);
                highScores.add(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
