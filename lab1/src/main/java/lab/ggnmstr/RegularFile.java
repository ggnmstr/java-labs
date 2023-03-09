package lab.ggnmstr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RegularFile extends File {

    public RegularFile(Path path, long size) {
        super(path);
        this.size = size;
    }

    @Override
    public String toString() {
        return getRealPath().getFileName() + " [" + size + " bytes]";
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<File> children) {

    }

}
