package lab.ggnmstr;

import java.io.PrintStream;

public class JduPrinter {
    private final PrintStream printStream;

    public JduPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void print(File curdir, Main.Options params){
        print(curdir,params,0);
    }

    private void print(File curdir, Main.Options params, int depth){
        if (depth == params.depth()) return;
        printStream.print("    ".repeat(depth));
        printStream.println(curdir);
        if (curdir instanceof SymlinkFile && !params.goLinks()) return;
        if (curdir.getChildren() == null) return;
        int displayed = 0;
        for (File x : curdir.getChildren()) {
            print(x, params,depth+1);
            displayed++;
            if (displayed == params.limit()) break;
        }
    }
}
