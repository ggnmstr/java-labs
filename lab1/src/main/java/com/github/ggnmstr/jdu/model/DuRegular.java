package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;
import java.util.List;

public class DuRegular extends DuFile {

    public DuRegular(Path path, long size) {
        super(path);
        this.size = size;
    }

    @Override
    public String toString() {
        return getRealPath().getFileName() + " [" + size + " bytes]";
    }

    @Override
    public List<DuFile> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<DuFile> children) {

    }

}
