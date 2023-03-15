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
        if (root instanceof DuSymlink symlink){
            symlink.setChild(build(symlink.getRealPath()));
        }
        if (root instanceof DuDirectory directory) {
            fillDirectory(directory);
        }
        return root;
    }

    private static DuFile createFile(Path path) {
        if (Files.isSymbolicLink(path)) {
            Path realpath;
            long linksize;
            try {
                realpath = path.toRealPath();
                linksize = Files.size(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new DuSymlink(realpath, linksize);
        }
        if (Files.isRegularFile(path)) {
            long size;
            try {
                size = Files.size(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new DuRegular(path, size);
        }
        if (Files.isDirectory(path)) {
            return new DuDirectory(path);
        }
        throw new AssertionError("Should not reach");
    }

    private void fillDirectory(DuDirectory duDir) {
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
