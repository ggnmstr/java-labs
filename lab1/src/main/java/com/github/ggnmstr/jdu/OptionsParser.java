package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.exception.DuParserException;

import java.util.Objects;

public class OptionsParser {

    private static final int DEFAULT_DEPTH = 5;
    private static final int DEFAULT_LIMIT = 5;
    private static final boolean FOLLOW_LINKS = false;

    public static Options parseParams(String[] args) throws DuParserException {
        int depth = DEFAULT_DEPTH;
        int limit = DEFAULT_LIMIT;
        boolean golinks = FOLLOW_LINKS;
        int i = 0;
        while (i < args.length - 1) {
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
            throw new DuParserException("Unknown option: " + args[i]);
        }
        return new Options(depth, limit, golinks);
    }

    public static int isPosInteger(String arg) throws DuParserException {
        int res;
        try {
            // CR: parse unsigned
            res = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new DuParserException("Invalid argument " + arg);
        }
        return res;
    }

}

record Options(int depth, int limit, boolean goLinks) {
}

