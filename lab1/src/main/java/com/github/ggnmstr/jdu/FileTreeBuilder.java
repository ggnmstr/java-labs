package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Build a file tree in specified root path
 * <p>
 * In case of symbolic link recursion does not create new instance
 * of class for every recursion iteration, instead uses already created
 * ones from HashMap of visited files.
 */
public class FileTreeBuilder {

    private final Map<Path, DuFile> visited = new HashMap<>();

    public DuFile build(Path path) {
        DuFile root = createFile(path);
        DuFile ex = visited.get(path);
        if (ex != null) return ex;
        visited.put(path, root);
        if (root instanceof DuSymlink symlink) {
            symlink.setChild(build(symlink.getRealPath()));
        }
        if (root instanceof DuDirectory directory) {
            fillDirectory(directory);
        }
        return root;
    }

    private static DuFile createFile(Path path) {
        try {
            if (Files.isSymbolicLink(path)) {
                return new DuSymlink(path.toRealPath(), path, Files.size(path));
            }
            if (Files.isRegularFile(path)) {
                return new DuRegular(path, Files.size(path));
            }
            if (Files.isDirectory(path)) {
                return new DuDirectory(path);
            }
        } catch (IOException e) {
            System.err.println("Error: " + path + " is not accessible, returning DuUnknown file type.");
        }
        return new DuUnknown(path);
    }

    private void fillDirectory(DuDirectory duDir) {
        long size = 0;
        List<DuFile> children = new ArrayList<>();
        try (Stream<Path> files = Files.list(duDir.getRealPath())) {
            files.forEach(x -> children.add(build(x)));
        } catch (IOException e) {
            System.err.println(duDir.getRealPath() + " is not accessible");
        }
        duDir.setChildren(children);
        for (DuFile x : children) {
            size += x.getSize();
        }
        duDir.setSize(size);
    }
}
