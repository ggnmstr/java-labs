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
        FileTreeBuilder builder = new FileTreeBuilder();
        File curdir = builder.buildrec(workpath);
        int[] params = parseParams(args);
        //DirectoryFile curdir = new DirectoryFile(workpath);
        jduPrint(curdir,5,0,5);
        //jduPrint(curdir, params[1], 0, params[0]);

    }

    public static void jduPrint(File curdir, int limit, int start, int maxdepth) {
        if (start == maxdepth) return;
        for (int i = 0; i < start; i++) {
            System.out.print("  ");
        }
        System.out.println(curdir);
        if (curdir.getChildren() == null) return;
        int displayed = 0;
        for (File x : curdir.getChildren()) {
            jduPrint(x, limit, start + 1, maxdepth);
            displayed++;
            if (displayed == limit) break;
        }
    }

    public static int[] parseParams(String[] args) {
        // depth, limit, L
        int[] params = {5, 5, 0};
        for (int i = 0; i < args.length; i++){
            if (args[i] == "-L") params[2] = 1;
            if (args[i] == "--depth" && isPosInteger(args[i+1]) > 0) params[0] = isPosInteger(args[i+1]);
            if (args[i] == "--limit" && isPosInteger(args[i+1]) > 0) params[1] = isPosInteger(args[i+1]);
        }
        return params;
    }

    public static int isPosInteger(String arg){
        int res = -1;
        try{
            res = Integer.parseInt(arg);
        } catch (NumberFormatException e){

        }
        return res;
    }
}