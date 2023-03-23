package com.github.ggnmstr.jdu.model;

import com.github.ggnmstr.jdu.DuVisitor;

import java.nio.file.Path;

public class DuSymlink extends DuFile {
    private DuFile child;

    private final Path linkPath;

    public DuSymlink(Path realPath, Path linkPath, long linkSize) {
        super(realPath,linkSize);
        this.linkPath = linkPath;
    }

    @Override
    public String toString() {
        return linkPath.getFileName() + " [" + size + " bytes]" +
                " (symlink to " + child.getRealPath() + " [" + child.getSize() + " bytes])";
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void accept(DuVisitor visitor) {
        visitor.visit(this);
    }

    public DuFile getChild() {
        return child;
    }

    public void setChild(DuFile child) {
        this.child = child;
    }

}
