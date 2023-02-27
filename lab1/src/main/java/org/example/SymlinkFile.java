package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SymlinkFile extends File {

    static final long SYMLINK_SIZE = 4096;

    private ArrayList<File> children;

    private Path linkPath;

    public SymlinkFile(Path path){
        super(path);
        this.linkPath = path;
        this.children = new ArrayList<>();
        this.size = 0;
    }

    @Override
    public String toString() {
        try {
            String s = String.valueOf(this.realPath.toRealPath());
            return realPath.getFileName() + " (symlink to " + s + " ) [" + size + " bytes]";
        } catch (IOException e) {
            return realPath.getFileName() + " (symlink to " + realPath + " [" + size + " bytes]";

        }

    }

    @Override
    public List<File> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<File> children) {
        this.children = (ArrayList<File>) children;
    }

}
