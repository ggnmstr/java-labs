package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DuDirectory extends DuFile {

    private List<DuFile> children;

    public DuDirectory(Path path) {
        super(path);
        this.size = 0;
    }

    @Override
    public String toString() {
        return "/" + realPath.getFileName() + " [" + size + " bytes]";
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
