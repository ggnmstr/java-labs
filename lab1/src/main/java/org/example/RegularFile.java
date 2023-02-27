package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class RegularFile extends File {

    public RegularFile(Path path) {
        super(path);
        try {
            this.size = Files.size(path);
        } catch (IOException e) {
            System.err.println("RegularFile size error???");
        }

    }

    @Override
    public String toString() {
        return realPath.getFileName() + " [" + size + " bytes]";
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
    public void setChildren(List<File> children) {

    }

}
