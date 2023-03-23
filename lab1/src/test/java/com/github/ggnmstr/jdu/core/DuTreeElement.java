package com.github.ggnmstr.jdu.core;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public record DuTreeElement(Type type, String path, List<DuTreeElement> children) {

    public static DuFile tree(FileSystem fs, DuTreeElement root) {
        return buildTree(root, fs.getPath("/"));
    }

    private static DuFile buildTree(DuTreeElement treeElement, Path parentPath) {
        Path currentPath = parentPath.resolve(treeElement.path);
        if (treeElement.type == Type.FILE) {
            long size;
            try {
                size = Files.size(currentPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new DuRegular(currentPath,size);
        }
        List<DuFile> duChildren = treeElement.children.stream().map(c -> buildTree(c, currentPath)).toList();
        DuDirectory result = new DuDirectory(currentPath);
        result.setChildren(duChildren);
        return result;
    }

    public static DuTreeElement dir(String name, DuTreeElement... files) {
        return new DuTreeElement(Type.DIR, name, List.of(files));
    }

    public static DuTreeElement file(String name) {
        return new DuTreeElement(Type.FILE, name, null);
    }

    private enum Type {
        DIR,
        FILE
    }
}
