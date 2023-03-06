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
        }

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
