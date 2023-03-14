package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;
import com.github.ggnmstr.jdu.model.DuSymlink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTreeBuilder {

    private final Set<DuFile> visited = new HashSet<>();

    public DuFile build(Path path) {
        DuFile root = createFile(path);
        if (!visited.add(root)) {
            for (DuFile x : visited) {
                if (x.equals(root)) return x;
            }
        }
        if (Files.isDirectory(path)) {
            fillDirectory(root);
        }
        return root;
    }

    private static DuFile createFile(Path path) {
        Path realpath;
        try {
            // CR: do we need it?
            realpath = path.toRealPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Files.isSymbolicLink(path)) {
            return new DuSymlink(realpath);
        }
        if (Files.isRegularFile(realpath)) {
            long size;
            try {
                size = Files.size(realpath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new DuRegular(realpath, size);
        }
        if (Files.isDirectory(path)) {
            return new DuDirectory(realpath);
        }
        throw new AssertionError("Should not reach");
    }

    private void fillDirectory(DuFile duDir) {
        long size = 0;
        List<DuFile> children = new ArrayList<>();
        try {
            Files.list(duDir.getRealPath()).forEach(x -> children.add(build(x)));
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
