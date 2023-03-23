package com.github.ggnmstr.jdu.model;

import com.github.ggnmstr.jdu.DuVisitor;

import java.nio.file.Path;

public abstract class DuFile {

    // CR: private?
    protected final Path realPath;
    protected long size;
    protected DuFile(Path path) {
        this.realPath = path;
    }

    protected DuFile(Path path, long size){
        this.realPath = path;
        this.size = size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Path getRealPath() {
        return this.realPath;
    }

    public long getSize() {
        return this.size;
    }

    public abstract void accept(DuVisitor visitor);

    // CR: probably remove
    @Override
    public int hashCode() {
        return realPath.hashCode();
    }

    // CR: probably remove
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        return this.realPath.equals(((DuFile) obj).realPath);
    }
}
