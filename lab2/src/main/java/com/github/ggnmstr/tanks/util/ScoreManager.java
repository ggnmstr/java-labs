package com.github.ggnmstr.tanks.util;

import java.io.*;
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
            highScores.remove(10);
        }
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    public void saveToFile() {
        // CR: nio
        File file = new File("scores.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (Score entry : highScores) {
                pw.println(entry.playerName() + " : " + entry.value());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        // CR: same
        File file = new File("scores.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String namescore = line.split(" : ")[0];
                int valscore = Integer.parseInt(line.split(" : ")[1]);
                Score score = new Score(namescore,valscore);
                highScores.add(score);
            }
        } catch (IOException e) {
        }
    }

}
