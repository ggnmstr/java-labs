package org.example;

import java.util.List;

public interface File {

    public long getSize();

    List<File> getChildren();
}
