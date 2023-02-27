package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryFile extends File {
    private ArrayList<File> children;

    /*
    public DirectoryFile(Path path) {
        this.path = path;
        this.size = 0;
        children = new ArrayList<>();
        try {
            List<Path> inside = Files.list(path).toList();
            for (Path x : inside) {
                if (Files.isRegularFile(x)) {
                    children.add(new RegularFile(x));
                } else if (Files.isDirectory(x) && !Files.isSymbolicLink(x) ) {
                    children.add(new DirectoryFile(x));
                } else if (Files.isSymbolicLink(x)) {
                    //System.out.println(x.toRealPath());
                    children.add(new SymlinkFile(x));
                }
                //children.add(Files.isRegularFile(x) ? new RegularFile(x) : new DirectoryFile(x));
            }
        } catch (IOException e) {
            System.err.println("oops");
        }
        for (File x : children) {
            size += x.getSize();
        }
        Collections.sort(children, Collections.reverseOrder());
    }

     */

    public DirectoryFile(Path path) {
        super(path);
        this.size = 0;
        children = new ArrayList<>();
    }



    @Override
    public String toString() {
        return "/" + realPath.getFileName() + " [" + size + " bytes]";
    }


    @Override
    public List<File> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<File> children) {
        this.children = (ArrayList<File>) children;
    }

}
