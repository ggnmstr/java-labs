package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;
import com.github.ggnmstr.jdu.model.DuSymlink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

// CR: comment with how recursion
public class FileTreeBuilder {

    // CR: Map<Path, DuFile>
    private final Set<DuFile> visited = new HashSet<>();

    public DuFile build(Path path) {
        DuFile root = createFile(path);
        if (!visited.add(root)) {
            // CR: O(n)
            for (DuFile x : visited) {
                if (x.equals(root)) return x;
            }
        }
        if (root instanceof DuSymlink symlink) {
            // CR: maybe do this in createFile in Symlink ctor
            symlink.setChild(build(symlink.getRealPath()));
        }
        if (root instanceof DuDirectory directory) {
            fillDirectory(directory);
        }
        return root;
    }

    private static DuFile createFile(Path path) {

//        try {
//            if (Files.isDirectory()) {
//                createDirectory();
//            }
//        } catch (IOException e) {
//            throw new MyException(e);
//        }

        if (Files.isSymbolicLink(path)) {
            // CR: extract branches to methods
            Path realpath;
            long linksize;
            try {
                realpath = path.toRealPath();
                linksize = Files.size(path);
            } catch (IOException e) {
                // CR: log error
                // CR: continue building tree
                // CR: return new DuUnknownFile(path); || DuSymLink(null, -1);
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
        try(Stream<Path> files = Files.list(duDir.getRealPath())) {
            files.forEach(x -> children.add(build(x)));
        } catch (IOException e) {
            // CR: same
            System.err.println(duDir.getRealPath() + " is not accessible");
        }
        duDir.setChildren(children);
        for (DuFile x : children) {
            size += x.getSize();
        }
        duDir.setSize(size);
    }
}
