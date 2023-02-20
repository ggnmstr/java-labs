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
        jduPrint(curdir,3,0,3);

    }

    public static void jduPrint(File curdir,int limit, int start, int maxdepth){
        if (start == maxdepth) return;
        for (int i = 0; i < start; i++){
            System.out.print("  ");
        }
        System.out.println(curdir);
        if (curdir.getChildren() == null) return;
        int displayed = 0;
        for (File x : curdir.getChildren()){
            jduPrint(x,limit,start+1,maxdepth);
            displayed++;
            if (displayed == limit) break;
        }
    }

    public static int[] parseParams(String[] args) {
        // depth, limit, L
        int[] params = {5, 5, 0};
        return params;
    }
}