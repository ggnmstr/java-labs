package com.github.ggnmstr.tanks.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresWorker {
    public static void writeScore(String name, int newScore){
        // very bad function!!! xd
        File file = new File("scores.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> lines = new ArrayList<>();
        boolean added = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int linescore = Integer.parseInt(line.split(" ")[2]);
                if (!added && linescore < newScore){
                    lines.add(name + " : " + newScore);
                    added = true;
                }
                lines.add(line);
            }
            if (!added){
                lines.add(name + " : " + newScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getScores(){
        List<String> lines = new ArrayList<>();
        File file = new File("scores.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                int linescore = Integer.parseInt(line.split(" ")[2]);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /*
    public static void writeHighScore(String name, int score){
        File file = new File("scores.txt");
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //while (reader.)
    }
    public static void test123123(String name, int score){
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream("scores.txt",true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pw.append(name).append(" : ").append(String.valueOf(score)).append(String.valueOf('\n'));
        pw.close();
    }

    public static void get(){
        Scanner filereader;
        try {
            filereader = new Scanner(new File("scores.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (filereader.hasNextLine()){
            String[] data = filereader.nextLine().split(" ");
            String name = data[0];
            int score = Integer.parseInt(data[2]);
            System.out.println(name + " /\\ " + score);
        }
    }
     */
}
