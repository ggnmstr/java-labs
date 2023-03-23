package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.exception.DuParserException;
import com.github.ggnmstr.jdu.model.DuFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println(usage());
            return;
        }
        Options params;
        try {
            params = OptionsParser.parseParams(args);
        } catch (DuParserException e) {
            System.err.println(e.getMessage());
            System.err.println(usage());
            return;
        }
        if (params == null) {
            System.err.println(usage());
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
    public static String usage() {
        return """
                Usage: jdu [options] [directory]
                Available options:
                -L - follow symlinks 
                --depth <n> - display files n levels deeper than source
                --limit <n> - display top n files inside
                """;
    }
}