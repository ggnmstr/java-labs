package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuSymlink;

import java.io.PrintStream;

// CR: visitor / sealed classes
public class JduPrinter {
    private final PrintStream printStream;

    public JduPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void print(DuFile curdir, Options params){
        print(curdir,params,0);
    }

    private void print(DuFile curdir, Options params, int depth){
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(curdir);
        if (curdir instanceof DuSymlink && !params.goLinks()) return;
        if (curdir.getChildren() == null) return;
        int displayed = 0;
        for (DuFile x : curdir.getChildren()) {
            print(x, params,depth+1);
            displayed++;
            if (displayed == params.limit()) break;
        }
    }
}


/*
        switch (curdir) {
            RegularFile f -> ..;
            com.github.ggnmstr.jdu.model.CompoundFile f -> ...;
        }

        interface Visitor {
    void visit(RegularFile regularFile);
}

class Printer implements Visitor {

    @Override
    public void visit(RegularFile regularFile) {

    }

    public void visit(com.github.ggnmstr.jdu.model.CompoundFile file) {
        //
    }
}
 */

