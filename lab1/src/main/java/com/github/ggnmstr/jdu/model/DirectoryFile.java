package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DirectoryFile extends File {

    private List<File> children;

    public DirectoryFile(Path path) {
        super(path);
        this.size = 0;
        // CR: useless
        children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "/" + realPath.getFileName() + " [" + size + " bytes]";
    }

    @Override
    public List<File> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<File> children) {
        this.children = children;
    }

}
