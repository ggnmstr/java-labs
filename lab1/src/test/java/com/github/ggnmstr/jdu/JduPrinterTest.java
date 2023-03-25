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
import java.util.ArrayList;
import java.util.List;

public class JduPrinterTest extends DuTest {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);


    @Test
    public void testOneFile() throws IOException {
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));

        DuRegular duRegular = new DuRegular(Path.of("/foo.txt"),0);
        printer.print(duRegular);


        String output = outputStream.toString();
        String expected = "foo.txt [0 bytes]\n";
        TestCase.assertEquals(expected, output);

    }

    @Test
    public void testFileInsideDirectory() throws IOException {
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));

        DuDirectory directory = new DuDirectory(Path.of("/foo"));
        directory.setChildren(new ArrayList<> (List.of(new DuRegular(Path.of("/bar.txt"),0))));
        printer.print(directory);


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
