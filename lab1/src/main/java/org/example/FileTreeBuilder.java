package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTreeBuilder {
    HashSet<File> visited = new HashSet<>();

    public File build(Path path) {
        File root = createFile(path);
        if (root == null) return null;
        Deque<File> toVisit = new ArrayDeque<>();
        toVisit.add(root);
        while (!toVisit.isEmpty()) {
            File cur = toVisit.removeLast();
            List<File> children = getChildren(cur);
            if (children == null) continue;
            cur.setChildren(children);
            for (File x : children) {
                if (!visited.contains(x)) {
                    toVisit.add(x);
                    visited.add(x);
                }
            }
        }
        return root;

    }

    public File createFile(Path path) {
        if (Files.isRegularFile(path)) {
            return new RegularFile(path);
        } else if (Files.isDirectory(path) && !Files.isSymbolicLink(path)) {
            return new DirectoryFile(path);
        } else if (Files.isSymbolicLink(path)) {
            return new SymlinkFile(path);
        } else return null;
    }

    public List<File> getChildren(File file) {
        if (file instanceof RegularFile) return null;
        List<Path> inside;
        try {
            inside = Files.list(file.getRealPath()).toList();
        } catch (IOException e) {
            System.err.println("getchildren error");
            return null;
        }
        List<File> toreturn = new ArrayList<>();
        for (Path x : inside) {
            toreturn.add(createFile(x));
        }
        return toreturn;
    }
}
