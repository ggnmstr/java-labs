package lab.ggnmstr;

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
        Options params = parseParams(args);
        if (params == null) {
            usage();
            return;
        }
        FileTreeBuilder builder = new FileTreeBuilder();
        File curdir = builder.build(workpath);

        JduPrinter jduPrinter = new JduPrinter(System.out);
        jduPrinter.print(curdir,params);
    }

    record Options(int depth, int limit, boolean goLinks) {}

    public static Options parseParams(String[] args) {
        int depth = 5;
        int limit = 5;
        boolean golinks = false;
        int i = 1;
        while (i < args.length) {
            if (Objects.equals(args[i], "-L")) {
                golinks = true;
                i++;
                continue;
            }
            if (Objects.equals(args[i], "--depth") && isPosInteger(args[i + 1]) > 0) {
                depth = isPosInteger(args[i + 1]);
                i += 2;
                continue;
            }
            if (Objects.equals(args[i], "--limit") && isPosInteger(args[i + 1]) > 0) {
                limit = isPosInteger(args[i + 1]);
                i += 2;
                continue;
            }
            return null;
        }
        return new Options(depth, limit, golinks);
    }

    public static int isPosInteger(String arg) {
        int res = -1;
        try {
            res = Integer.parseInt(arg);
        } catch (NumberFormatException e) {

        }
        return res;
    }

    public static void usage() {
        System.out.println("Usage: jdu [directory] [options]");
    }
}