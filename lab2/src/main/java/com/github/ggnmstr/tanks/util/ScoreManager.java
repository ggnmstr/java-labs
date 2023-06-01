package com.github.ggnmstr.tanks.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreManager {

    private final List<Score> highScores;

    private static ScoreManager instance;

    private ScoreManager() {
        this.highScores = loadScores();
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
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
            Score newscore = new Score(name, score);
            highScores.add(i, newscore);
            if (highScores.size() >= 11) highScores.remove(10);
        }
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    public void saveToFile() {
        Path filePath = Path.of("scores.txt");
        try {
            String line = highScores.stream()
                    .map(entry -> entry.playerName() + " : " + entry.value())
                    .collect(Collectors.joining("\n"));
            Files.writeString(filePath, line, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Score> loadScores() {
        List<Score> loadedScores = new ArrayList<>();
        Path filePath = Path.of("scores.txt");
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return loadedScores;
            }
            List<String> lines = Files.readAllLines(filePath);
            lines.stream()
                    .map(line -> line.split(" : "))
                    .map(parts -> new Score(parts[0], Integer.parseInt(parts[1])))
                    .forEach(loadedScores::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedScores;
    }

}
