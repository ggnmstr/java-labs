package com.github.ggnmstr.tanks.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordManager {

    // CR: restrict number of scores
    private final List<Score> highScores = new ArrayList<>();

    private static RecordManager instance;

    private RecordManager(){
        loadFromFile();
    }

    public static RecordManager getInstance(){
        if (instance == null){
            instance = new RecordManager();
        }
        return instance;
    }

    public void addScore(String name, int score) {
        Score newscore = new Score(name,score);
        int i = 0;
        for (Score curscore : highScores) {
            if (score > curscore.score()) {
                break;
            }
            i++;
        }
        highScores.add(i,newscore);
    }

    public List<Score> getHighScores() {
        return highScores;
    }

    public void saveToFile() {
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
                pw.println(entry.name() + " : " + entry.score());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
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
