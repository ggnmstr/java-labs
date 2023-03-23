package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;
import com.github.ggnmstr.jdu.model.DuSymlink;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

// CR: comment with examples of printing
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
        // CR: use PrintStream
        System.out.print("    ".repeat(depth));
        System.out.println(symlink);
        if (params.goLinks()) symlink.getChild().accept(this);
    }

    @Override
    public void visit(DuDirectory directory) {
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(directory);
        depth++;
        List<DuFile> children = directory.getChildren();
        // CR: use standard comparator Comparator.comparingLong()
        children.sort((o1, o2) -> (int) (o2.getSize() - o1.getSize()));
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
