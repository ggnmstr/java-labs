package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.File;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

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
        // CR: custom exception
        Options params = OptionsParser.parseParams(args);
        if (params == null) {
            usage();
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        File curdir = builder.build(workpath);

        JduPrinter jduPrinter = new JduPrinter(System.out);
        jduPrinter.print(curdir,params);
    }

    public static void usage() {
        System.out.println("Usage: jdu [directory] [options]");
    }
}