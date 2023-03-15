package com.github.ggnmstr.jdu.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DuSymlink extends DuFile {
    private DuFile child;

    // CR: remove
    private final Path linkPath;
    private long linkSize;

    public DuSymlink(Path path) {
        super(path);
        this.linkPath = path;
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

    public DuFile getChild() {
        return child;
    }

    public void setChild(DuFile child) {
        this.child = child;
    }

}
