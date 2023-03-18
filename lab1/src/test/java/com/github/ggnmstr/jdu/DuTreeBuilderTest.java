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
        Path fooPath = fs.getPath("/root/foo");
        Files.createDirectory(fooPath);
        Path foo2Path = fs.getPath("/root/foo2");
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
        Path path1 = fs.getPath("/root/txt1");
        Path path2 = fs.getPath("/root/txt2");
        Path path3 = fs.getPath("/root/txt3");
        Path path4 = fs.getPath("/root/txt4");
        Path path5 = fs.getPath("/root/txt5");
        Files.createDirectory(path1);
        Files.createDirectory(path2);
        Files.createDirectory(path3);
        Files.createDirectory(path4);
        Files.createDirectory(path5);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile actual = builder.build(rootPath);
        DuFile expected = tree(fs, dir("root", file("txt1"),file("txt2"),
                file("txt3"),file("txt4"),file("txt5")));

        TestCase.assertEquals(expected, actual);



    }

}
