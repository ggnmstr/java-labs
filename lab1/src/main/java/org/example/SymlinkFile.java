package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SymlinkFile extends File {
    private ArrayList<File> children;

    private Path linkPath;
    private long linkSize;

    public SymlinkFile(Path path){
        super(path);
        this.linkPath = path;
        this.children = new ArrayList<>();
        this.size = 0;
        try {
            this.linkSize = Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        try {
            String s = String.valueOf(this.realPath.toRealPath());
            return linkPath.getFileName() + " (symlink to " + s + " ) [" + linkSize + " bytes]";
        } catch (IOException e) {
            return linkPath.getFileName() + " (symlink to " + realPath + " [" + linkSize + " bytes]";

        }

    }

    @Override
    public long getSize() {
        return this.linkSize;
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
