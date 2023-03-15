package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;

public class DuRegular extends DuFile {

    public DuRegular(Path path, long size) {
        super(path);
        this.size = size;
    }

    @Override
    public String toString() {
        return getRealPath().getFileName() + " [" + size + " bytes]";
    }

}
