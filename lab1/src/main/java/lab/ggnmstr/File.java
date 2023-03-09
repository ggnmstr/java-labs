package lab.ggnmstr;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public abstract class File implements Comparable<File> {

    protected Path realPath;
    protected long size;

    protected File(Path path) {
        this.realPath = path;
    }

    abstract List<File> getChildren();

    abstract public void setChildren(List<File> children);

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
    public int compareTo(File o) {
        return (int) (this.size - o.getSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(realPath);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File)) return false;
        return this.realPath.equals(((File) obj).getRealPath()) && this.getClass().equals(obj.getClass());
    }

}