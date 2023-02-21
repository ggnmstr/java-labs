package org.example;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SymlinkFile implements File {

    private Path path;
    private ArrayList<File> children;
    private long size;

    public SymlinkFile(Path path){
        this.path = path;
        this.size = 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public int compareTo(File o) {
        return 0;
    }
}
