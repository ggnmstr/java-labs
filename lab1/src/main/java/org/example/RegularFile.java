package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RegularFile implements File {
    private final Path path;
    private long size;

    public RegularFile(Path path) {
        this.path = path;
        try {
            this.size = Files.size(path);
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return path.getFileName() + " [" + size + "] bytes";
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public int compareTo(File o) {
        return (int) (size - o.getSize());
    }
}
