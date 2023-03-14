package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuFile;

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
            return;
        }
        // CR: last argument
        Path pathfromarg = Paths.get(args[0]);
        Path workpath;
        try {
            // CR: do we need it?
            workpath = pathfromarg.toRealPath();
        } catch (IOException x) {
            System.err.println("No such file or directory: " + pathfromarg);
            return;
        }
        Options params;
        try {
            params = OptionsParser.parseParams(args);
        } catch (ParserException e){
            System.err.println(e.getMessage());
            return;
        }
        if (params == null) {
            usage();
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        DuFile curdir = builder.build(workpath);

        JduPrinter jduPrinter = new JduPrinter(System.out);
        jduPrinter.print(curdir,params);
    }

    public static void usage() {
        System.out.println("Usage: jdu [directory] [options]");
    }
}