package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SymlinkFile implements File {
    private Path path;
    private long size;

    public SymlinkFile(Path path){
        this.path = path;
        try {
            this.size = Files.size(this.path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "/" + path.getFileName() + " [" + size + " bytes]";
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public int compareTo(File o) {
        return 0;
    }
}
