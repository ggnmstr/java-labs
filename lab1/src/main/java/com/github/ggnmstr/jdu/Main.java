package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.exception.DuParserException;
import com.github.ggnmstr.jdu.model.DuFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
            return;
        }
        Options params;
        try {
            params = OptionsParser.parseParams(args);
        } catch (DuParserException e) {
            System.err.println(e.getMessage());
            // CR: System.err usage
            return;
        }
        if (params == null) {
            usage();
            return;
        }
        Path rootPath = Paths.get(args[args.length - 1]);
        if (!Files.exists(rootPath)) {
            System.err.println("No such file or directory: " + rootPath);
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        DuFile root = builder.build(rootPath);

        JduPrinter jduPrinter = new JduPrinter(System.out, params);
        jduPrinter.print(root);
    }

    // CR: options description
    // C: text block
    public static void usage() {
        System.out.println("Usage: jdu [directory] [options]");
    }
}