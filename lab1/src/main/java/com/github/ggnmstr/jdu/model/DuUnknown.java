package com.github.ggnmstr.jdu.model;

import com.github.ggnmstr.jdu.DuVisitor;

import java.nio.file.Path;

public class DuUnknown extends DuFile{
    public DuUnknown(Path path) {
        super(path);
    }

    @Override
    public void accept(DuVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return realPath + " [UNKNOWN]";
    }
}
