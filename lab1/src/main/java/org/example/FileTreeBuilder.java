package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTreeBuilder {
    HashSet<File> visited = new HashSet<>();

    public File build(Path path) {
        File root = createFile(path);
        if (root == null || visited.contains(root)) return null;
        visited.add(root);
        if (!(root instanceof RegularFile)) {
            long size = 0;
            ArrayList<File> children = new ArrayList<>();
            try {
                List<Path> inside = Files.list(path).toList();
                for (Path x : inside) {
                    File kid = build(x);
                    if (kid == null) continue;
                    children.add(kid);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
            Collections.sort(children, Collections.reverseOrder());
            root.setChildren(children);
            for (File x : children) {
                size += x.getSize();
            }
            root.setSize(size);
        }
        return root;
    }

    /*
    public File build(Path path) {
        File root = createFile(path);
        if (root == null) return null;
        Deque<File> toVisit = new ArrayDeque<>();
        toVisit.add(root);
        Stack<File> dirStack = new Stack<>();
        while (!toVisit.isEmpty()) {
            File cur = toVisit.removeLast();
            List<File> children = getChildren(cur);
            if (children == null) continue;
            dirStack.push(cur);
            cur.setChildren(children);
            Collections.sort(children,Collections.reverseOrder());
            for (File x : children) {
                if (!visited.contains(x)) {
                    toVisit.add(x);
                    visited.add(x);
                }
            }
        }
        return root;
    }
    */
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
