package org.example;

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: jdu [options] directory");
            return;
        }
        Path pathfromarg = Paths.get(args[0]);
        Path workpath;
        try {
            workpath = pathfromarg.toRealPath();
        } catch (IOException x) {
            System.err.println("No such file or directory: " + pathfromarg);
            return;
        }
        DirectoryFile curdir = new DirectoryFile(workpath);
        for (File x : curdir.getChildren()) {
            System.out.println(x);
        }

    }

    public static int[] parseParams(String[] args) {
        // depth, limit, L
        int[] params = {5, 5, 0};
        return params;
    }
}