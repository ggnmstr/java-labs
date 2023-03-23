package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.core.DuTest;
import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuFile;
import com.github.ggnmstr.jdu.model.DuRegular;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JduPrinterTest extends DuTest {

    @Test
    public void testOneFile() throws IOException {
        // CR: field
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));

        FileSystem fs = fileSystem();
        Path fooPath = fs.getPath("/foo.txt");
        Files.createFile(fooPath);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile file = builder.build(fooPath);
        // CR:
        // DuDirectory duDirectory = new DuDirectory("bar");
        // duDirectory.setChildren(List.of(new DuRegular("foo")));
        printer.print(file);


        String output = outputStream.toString();
        String expected = "foo.txt [0 bytes]\n";
        TestCase.assertEquals(expected, output);

    }

    @Test
    public void testFileInsideDirectory() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));

        FileSystem fs = fileSystem();

        Path fooPath = fs.getPath("/foo");
        Files.createDirectory(fooPath);

        Path barPath = fs.getPath("/foo/bar.txt");
        Files.createFile(barPath);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile file = builder.build(fooPath);
        printer.print(file);


        String output = outputStream.toString();
        String expected = """
                /foo [0 bytes]
                    bar.txt [0 bytes]
                """;
        TestCase.assertEquals(expected, output);
    }

    // CR: check if options work
    // CR: check every type of file as a root
    // CR: check directories with different content
}
