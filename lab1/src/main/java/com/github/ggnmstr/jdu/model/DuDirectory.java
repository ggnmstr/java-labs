package com.github.ggnmstr.jdu.model;

import com.github.ggnmstr.jdu.DuVisitor;

import java.nio.file.Path;
import java.util.List;

public class DuDirectory extends DuFile {

    private List<DuFile> children;

    public DuDirectory(Path path) {
        super(path);
        this.size = 0;
    }

    @Override
    public void accept(DuVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "/" + realPath.getFileName() + " [" + size + " bytes]";
    }

    public List<DuFile> getChildren() {
        return children;
    }

    public void setChildren(List<DuFile> children) {
        this.children = children;
    }

}
