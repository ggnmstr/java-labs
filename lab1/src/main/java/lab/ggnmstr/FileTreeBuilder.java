package lab.ggnmstr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTreeBuilder {

    Set<File> visited = new HashSet<>();

    public File build(Path path) {
        File root = createFile(path);
        if (!visited.add(root)) return null;
        if (Files.isDirectory(path)) {
            fillDirectory(root);
        }
        return root;
    }

    private static File createFile(Path path) {
        // CR: move here: path.toRealPath()
        if (Files.isSymbolicLink(path)) {
            return new SymlinkFile(path);
        }
        if (Files.isRegularFile(path)) {
            // CR: move size get here
            return new RegularFile(path);
        }
        if (Files.isDirectory(path)) {
            return new DirectoryFile(path);
        }
        throw new AssertionError("Should not reach");
    }

    private void fillDirectory(File dirFile){
        long size = 0;
        List<File> children = new ArrayList<>();
        try {
            List<Path> inside = Files.list(dirFile.getRealPath()).toList();
            for (Path x : inside) {
                File kid = build(x);
                if (kid == null) continue;
                children.add(kid);
            }
        } catch (IOException e) {
            System.err.println(dirFile.getRealPath() + " is not accessible");
        }
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
