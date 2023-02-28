package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

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
        File curdir = builder.build(workpath);
        int[] params = parseParams(args);
        //DirectoryFile curdir = new DirectoryFile(workpath);
        //jduPrint(curdir,5,0,5,false);
        jduPrint(curdir, params[1], 0, params[0],params[2]);

    }

    public static void jduPrint(File curdir, int limit, int start, int maxdepth, int golinks) {
        //System.out.println("L: "+limit + " D:" + maxdepth);
        if (start == maxdepth) return;
        for (int i = 0; i < start; i++) {
            System.out.print("  ");
        }
        System.out.println(curdir);
        if (curdir instanceof SymlinkFile && golinks == 0) return;
        if (curdir.getChildren() == null) return;
        int displayed = 0;
        for (File x : curdir.getChildren()) {
            jduPrint(x, limit, start + 1, maxdepth,golinks);
            displayed++;
            if (displayed == limit) break;
        }
    }

    public static int[] parseParams(String[] args) {
        // depth, limit, L
        int[] params = {5, 5, 0};
        for (int i = 0; i < args.length; i++){
            if (Objects.equals(args[i], "-L")) params[2] = 1;
            if (Objects.equals(args[i], "--depth") && isPosInteger(args[i+1]) > 0) params[0] = isPosInteger(args[i+1]);
            if (Objects.equals(args[i], "--limit") && isPosInteger(args[i+1]) > 0) params[1] = isPosInteger(args[i+1]);
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