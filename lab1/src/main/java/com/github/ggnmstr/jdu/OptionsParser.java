package com.github.ggnmstr.jdu;

import java.util.Objects;

public class OptionsParser {

    private static final int DEFAULT_DEPTH = 5;
    private static final int DEFAULT_LIMIT = 5;
    private static final boolean FOLLOW_LINKS = false;

    public static Options parseParams(String[] args) {
        int depth = DEFAULT_DEPTH;
        int limit = DEFAULT_LIMIT;
        boolean golinks = FOLLOW_LINKS;
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
            // CR: rethrow custom exception
        }
        return res;
    }

}

record Options(int depth, int limit, boolean goLinks) {
}

