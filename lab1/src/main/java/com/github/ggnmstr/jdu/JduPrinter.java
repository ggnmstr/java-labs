package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;
import com.github.ggnmstr.jdu.model.DuSymlink;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

/**
 * Prints DuFile (result of FileTreeBuilder's build() method)
 * For example:
 *  /foo [12 bytes]
 *      bar [8 bytes]
 *      baz [4 bytes]
 * Uses Options (limit, depth, followlinks)
 * In case of symlink recursion prints it until depth/limit reached.
 * For example (with depth = 5):
 * /Videos [44 bytes]
 *     musiclink [44 bytes] (symlink to /home/User/Music [25743 bytes])
 *     /Music [25743 bytes]
 *         videolink [18 bytes] (symlink to /home/User/Videos [44 bytes])
 *         /Videos [44 bytes]
 *             musiclink [44 bytes] (symlink to /home/User/Music [25743 bytes])
 *             /Music [25743 bytes]
 *                 videolink [18 bytes] (symlink to /home/User/Videos [44 bytes])
 *                 /Videos [44 bytes]
 */
public class JduPrinter implements DuVisitor {

    private final PrintStream printStream;
    private final Options params;

    private int depth = 0;

    public JduPrinter(PrintStream printStream, Options params) {
        this.printStream = printStream;
        this.params = params;
    }

    public void print(DuFile curdir) {
        curdir.accept(this);
    }

    @Override
    public void visit(DuSymlink symlink) {
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(symlink);
        if (params.goLinks()) symlink.getChild().accept(this);
    }

    @Override
    public void visit(DuDirectory directory) {
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(directory);
        depth++;
        List<DuFile> children = directory.getChildren();
        children.sort(Comparator.comparingLong(DuFile::getSize).reversed());
        int i = 0;
        for (DuFile x : children) {
            x.accept(this);
            i++;
            if (i == params.limit()) break;
        }
        depth--;
    }

    @Override
    public void visit(DuRegular regular) {
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(regular);
    }
}
