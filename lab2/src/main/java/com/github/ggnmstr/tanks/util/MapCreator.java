package com.github.ggnmstr.tanks.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class MapCreator {
    public static char[][] create(){
        // CR: do not continue with partial map
        int rows = 52;
        int columns = 52;
        char[][] mapTemplate = new char[rows][columns];
        URL path = Thread.currentThread().getContextClassLoader().getResource("maps/level2.txt");
        File file;
        try {
            file = new File(path.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            generateDefaultMap();
        }
        for (int row = 0; scanner.hasNextLine() && row < rows; row++) {
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < columns && i < chars.length; i++) {
                mapTemplate[row][i] = chars[i];
            }
        }
        return mapTemplate;
    }

    private static void generateDefaultMap() {
        File file = new File("resources/maps/level1.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
}
