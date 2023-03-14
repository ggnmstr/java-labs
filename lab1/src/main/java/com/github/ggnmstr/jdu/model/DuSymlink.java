package com.github.ggnmstr.jdu.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DuSymlink extends DuFile {
    // Cross CR: private File child;
    private List<DuFile> children;

    // CR: remove
    private final Path linkPath;
    private long linkSize;

    public DuSymlink(Path path){
        super(path);
        this.linkPath = path;
        this.children = new ArrayList<>();
        // CR: use instead of linkSize
        this.size = 0;
        // CR: move to callee
        try {
            this.linkSize = Files.size(path);
        } catch (IOException e) {
        }
    }

    @Override
    public String toString() {
        return linkPath.getFileName() + " (symlink to " + realPath + ") [" + linkSize + " bytes]";
    }

    @Override
    public long getSize() {
        return this.linkSize;
    }

    @Override
    public List<DuFile> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<DuFile> children) {
        this.children = children;
    }

}