package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;
import java.util.List;

public abstract class DuFile {

    protected final Path realPath;
    protected long size;

    protected DuFile(Path path) {
        this.realPath = path;
    }

    public abstract List<DuFile> getChildren();

    public abstract void setChildren(List<DuFile> children);

    public void setSize(long size){
        this.size = size;
    }

    public Path getRealPath() {
        return this.realPath;
    }

    public long getSize() {
        return this.size;
    }

    @Override
    public int hashCode() {
        return realPath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        return this.realPath.equals(((DuFile)obj).realPath);
    }
}
