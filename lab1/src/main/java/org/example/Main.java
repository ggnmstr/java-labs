// Cross CR: rename package :D
package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
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
        int[] params = parseParams(args);
        if (params == null) {
            usage();
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        File curdir = builder.build(workpath);
        // Cross CR: make try-catch
        jduPrint(curdir, params[1], 0, params[0],params[2]);

    }

//    CR: record Options(int limit, boolean followSLink) {}

    public static void jduPrint(File curdir, int limit, int start, int maxdepth, int golinks) {
        if (start == maxdepth) return;
        // CR: make start a field
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
        // Cross CR: record
        int[] params = {5, 5, 0};
        int i = 1;
        while (i < args.length){
            // Cross CR: switch-case
            if (Objects.equals(args[i], "-L")) {
                params[2] = 1;
                i++;
                continue;
            }
            if (Objects.equals(args[i], "--depth") && isPosInteger(args[i+1]) > 0) {
                params[0] = isPosInteger(args[i+1]);
                i+=2;
                continue;
            }
            if (Objects.equals(args[i], "--limit") && isPosInteger(args[i+1]) > 0) {
                params[1] = isPosInteger(args[i+1]);
                i+=2;
                continue;
            }
            return null;
        }
        return params;
    }

    public static int isPosInteger(String arg){
        // throw something
        int res = -1;
        try{
            res = Integer.parseInt(arg);
        } catch (NumberFormatException e){

        }
        return res;
    }

    public static void usage(){
        System.out.println("Usage: jdu [options] directory");
    }
}