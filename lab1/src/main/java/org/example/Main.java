package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.stream.Stream;
import org.example.JduFileVisitor;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: jdu [options] directory");
            return;
        }
        /*
        for (String arg : args){
            System.out.println(arg);
        }
         */
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Path fromarg = Paths.get(args[0]);
        Path workdir;
        try {
            workdir = fromarg.toRealPath();
        } catch (NoSuchFileException x) {
            System.err.println("No such file or directory: " + fromarg);
            return;
        }
        System.out.println(workdir);
        listShit(workdir,0,2);
        /*
        JduFileVisitor jfv = new JduFileVisitor();
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(workdir,opts,3,jfv);
         */
        /*
        System.out.println(curdir);
        Stream<Path> stream = Files.list(curdir);
        for (Path x : stream.toList()){
            System.out.print(x.getFileName() + " ");
            System.out.println();
        }
         */
    }

    public static void listShit(Path path, int level, int maxlevel) throws IOException {
        for (int i = 0; i < level; i++){
            System.out.print("\t");
        }
        if (Files.isRegularFile(path)){
            System.out.print(path);
            System.out.println(Files.size(path));
        }
        if (Files.isDirectory(path)){
            if (level == maxlevel) {
                System.out.println(path);
                return;
            }
            Stream<Path> stream = Files.list(path);
            ArrayList<Path> inside = new ArrayList<Path>(stream.toList());
            for (Path x : inside){
                listShit(x,level+1, maxlevel);
            }
        }
    }

    public static int[] parseParams(String[] args){
        // depth, limit, L
        int[] params = {5,5,0};
        return params;
    }

    public static long getDirectorySize(Path dir){
        // WARNING : INDUS CODE
        long size = 0;
        try (Stream<Path> walk = Files.walk(dir)){
            size = walk
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e){
                            System.out.printf("Failed to get size of %s%n%s", p, e);
                            return 0L;
                        }
                    })
                    .sum();
        }
        catch (IOException e){
            System.out.printf("IO errors %s", e);
        }

        return size;
    }
}