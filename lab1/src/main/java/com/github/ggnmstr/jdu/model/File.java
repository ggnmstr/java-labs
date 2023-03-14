package com.github.ggnmstr.jdu.model;

import java.nio.file.Path;
import java.util.List;

// CR: rename to DuFile, also rename inheritors
public abstract class File implements Comparable<File> {

    protected final Path realPath;
    protected long size;

    protected File(Path path) {
        this.realPath = path;
    }

    public abstract List<File> getChildren();

    public abstract void setChildren(List<File> children);

    public void setSize(long size){
        this.size = size;
    }

    public Path getRealPath() {
        return this.realPath;
    }

    public long getSize() {
        return this.size;
    }

    // CR: better to use comparator
    @Override
    public int compareTo(File o) {
        return (int) (this.size - o.getSize());
    }

    @Override
    public int hashCode() {
        return realPath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!this.getClass().equals(obj.getClass())) return false;
        return this.realPath.equals(((File)obj).realPath);
    }
}
