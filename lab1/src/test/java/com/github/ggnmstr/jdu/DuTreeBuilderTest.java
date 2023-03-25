package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.core.DuTest;
import com.github.ggnmstr.jdu.model.DuFile;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.ggnmstr.jdu.core.DuTreeElement.*;

public class DuTreeBuilderTest extends DuTest {

    @Test
    public void testOneFileInDirectory() throws IOException {
        FileSystem fs = fileSystem();
        Path fooPath = fs.getPath("/foo");
        Files.createDirectory(fooPath);
        Path barPath = fooPath.resolve("bar.txt");
        Files.createFile(barPath);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile actual = builder.build(fooPath);
        DuFile expected = tree(fs, dir("foo", file("bar.txt")));
        TestCase.assertEquals(expected, actual);
    }

    @Test
    public void testTwoEmptyDirectories() throws IOException {
        FileSystem fs = fileSystem();
        Path rootPath = fs.getPath("/root");
        Files.createDirectory(rootPath);
        Path fooPath = rootPath.resolve("foo");
        Files.createDirectory(fooPath);
        Path foo2Path = rootPath.resolve("foo2");
        Files.createDirectory(foo2Path);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile actual = builder.build(rootPath);
        DuFile expected = tree(fs, dir("root", dir("foo"),dir("foo2")));
        TestCase.assertEquals(expected, actual);
    }

    @Test
    public void testManyFilesInsideDirectory() throws IOException {
        FileSystem fs = fileSystem();
        Path rootPath = fs.getPath("/root");
        Files.createDirectory(rootPath);
        Path path1 = rootPath.resolve("txt1");
        Path path2 = rootPath.resolve("txt2");
        Path path3 = rootPath.resolve("txt3");
        Path path4 = rootPath.resolve("txt4");
        Path path5 = rootPath.resolve("txt5");
        Files.createFile(path1);
        Files.createFile(path2);
        Files.createFile(path3);
        Files.createFile(path4);
        Files.createFile(path5);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile actual = builder.build(rootPath);
        DuFile expected = tree(fs, dir("root", file("txt1"),file("txt2"),
                file("txt3"),file("txt4"),file("txt5")));

        TestCase.assertEquals(expected, actual);
    }

    @Test
    public void testRegularRoot() throws IOException {
        FileSystem fs = fileSystem();
        Path rootPath = fs.getPath("/root");
        Files.createFile(rootPath);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile actual = builder.build(rootPath);
        DuFile expected = tree(fs,file("root"));

        TestCase.assertEquals(expected,actual);
    }

    /*
    @Test
    public void testSymlink() throws IOException {
        FileSystem fs = fileSystem();
        Path rootpath = fs.getPath("/root");
        Path dir1 = fs.getPath("/root/dir1");
        Path dir2 = fs.getPath("/root/dir2");
        Path l1 = fs.getPath("/root/dir1/l1");
        Path l2 = fs.getPath("/root/dir2/l2");

        Files.createDirectory(rootpath);
        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Files.createSymbolicLink(l1,dir2);
        Files.createSymbolicLink(l2,dir1);

        FileTreeBuilder builder = new FileTreeBuilder();
        DuFile actual = builder.build(rootpath);
        DuFile expected = tree(fs,dir("root",dir("dir1"),dir("dir2")));

        TestCase.assertEquals(expected,actual);


    }

     */
    // CR: test symlinks, test recursion
}
