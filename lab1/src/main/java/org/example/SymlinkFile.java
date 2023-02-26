package org.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SymlinkFile extends File {

    static final long SYMLINK_SIZE = 4096;

    public SymlinkFile(Path path){
        try {
            this.realPath = path.toRealPath();
        } catch (IOException e) {
            System.err.println("symlink construct error");
        }
        this.size = 0;
    }

    @Override
    public String toString() {
        return "/" + realPath.getFileName() + " [" + size + " bytes]";
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<File> children) {

    }

}
