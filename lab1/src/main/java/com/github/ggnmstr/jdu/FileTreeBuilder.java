package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DirectoryFile;
import com.github.ggnmstr.jdu.model.File;
import com.github.ggnmstr.jdu.model.RegularFile;
import com.github.ggnmstr.jdu.model.SymlinkFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTreeBuilder {

    // CR: private final
    Set<File> visited = new HashSet<>();

    public File build(Path path) {
        File root = createFile(path);
        if (!visited.add(root)) {
            for (File x : visited) {
                if (x.equals(root)) return x;
            }
        }
        if (Files.isDirectory(path)) {
            fillDirectory(root);
        }
        return root;
    }

    private static File createFile(Path path) {
        Path realpath;
        try {
            // CR: do we need it?
            realpath = path.toRealPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.isSymbolicLink(path)) {
            return new SymlinkFile(realpath);
        }
        if (Files.isRegularFile(realpath)) {
            long size;
            try {
                size = Files.size(realpath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new RegularFile(realpath, size);
        }
        if (Files.isDirectory(path)) {
            return new DirectoryFile(realpath);
        }
        throw new AssertionError("Should not reach");
    }

    private void fillDirectory(File dirFile) {
        long size = 0;
        List<File> children = new ArrayList<>();
        try {
            List<Path> inside = Files.list(dirFile.getRealPath()).toList();
            // CR: use stream
            for (Path x : inside) {
                File kid = build(x);
                children.add(kid);
            }
        } catch (IOException e) {
            System.err.println(dirFile.getRealPath() + " is not accessible");
        }
        // CR: move to print stage
        children.sort(Collections.reverseOrder());
        //  CR: new Directory(...)?
        dirFile.setChildren(children);
        for (File x : children) {
            size += x.getSize();
        }
        dirFile.setSize(size);
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
    */
}
