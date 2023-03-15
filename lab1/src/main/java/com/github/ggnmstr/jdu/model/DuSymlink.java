package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;

public class DuSymlink extends DuFile {
    private DuFile child;
    private final Path linkPath;
    private final long linkSize;

    public DuSymlink(Path path, long linkSize) {
        super(path);
        this.linkPath = path;
        this.linkSize = linkSize;
    }

    @Override
    public String toString() {
        return linkPath.getFileName() + " [" + linkSize + " bytes]" +
                " (symlink to " + realPath + " [" + child.getSize() + " bytes])";
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
