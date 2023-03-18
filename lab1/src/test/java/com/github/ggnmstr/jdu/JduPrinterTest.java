package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.core.DuTest;
import com.github.ggnmstr.jdu.model.DuFile;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class JduPrinterTest extends DuTest {

    @Test
    public void testOneFileInDirectory() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true, "UTF-8");
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));


        FileSystem fs = fileSystem();
        Path fooPath = fs.getPath("/foo.txt");
        Files.createFile(fooPath);

        FileTreeBuilder builder = new FileTreeBuilder();

        DuFile file = builder.build(fooPath);
        printer.print(file);


        String output = outputStream.toString();
        String expected = "foo.txt [0 bytes]\n";
        TestCase.assertEquals(expected, output);

    }
}
