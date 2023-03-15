package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuFile;

import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
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
        Path pathfromarg = Paths.get(args[args.length-1]);
        if (!Files.exists(pathfromarg)){
            System.err.println("No such file or directory: " + pathfromarg);
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        DuFile curdir = builder.build(pathfromarg);

        JduPrinter jduPrinter = new JduPrinter(System.out, params);
        jduPrinter.print(curdir);
    }

    public static void usage() {
        System.out.println("Usage: jdu [directory] [options]");
    }
}