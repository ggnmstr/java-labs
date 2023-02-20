package org.example;

import java.util.List;

public interface File extends Comparable<File> {

    public long getSize();

    List<File> getChildren();
}
