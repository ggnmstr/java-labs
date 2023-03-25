package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.core.DuTest;
import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuRegular;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JduPrinterTest extends DuTest {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);


    @Test
    public void testOneFile() {
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));

        DuRegular duRegular = new DuRegular(Path.of("/foo.txt"),0);
        printer.print(duRegular);


        String output = outputStream.toString();
        String expected = "foo.txt [0 bytes]\n";
        TestCase.assertEquals(expected, output);

    }

    @Test
    public void testFileInsideDirectory() {
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

    @Test
    public void testDepthOption() {
        JduPrinter printer = new JduPrinter(printStream,new Options(2,5,true));
        DuDirectory rootdir = new DuDirectory(Path.of("/root"));

        DuDirectory l1 = new DuDirectory(Path.of("/root/l1"));
        DuDirectory l2 = new DuDirectory(Path.of("root/l1/l2"));
        DuDirectory l3 = new DuDirectory(Path.of("root/l1/l2/l3"));
        DuDirectory l4 = new DuDirectory(Path.of("root/l1/l2/l3/l4"));

        l4.setChildren(new ArrayList<>());
        l3.setChildren(new ArrayList<> (List.of(l4)));
        l2.setChildren(new ArrayList<> (List.of(l3)));
        l1.setChildren(new ArrayList<> (List.of(l2)));
        rootdir.setChildren(new ArrayList<> (List.of(l1)));

        printer.print(rootdir);
        printer = new JduPrinter(printStream,new Options(3,10,true));
        String expected = """
                /root [0 bytes]
                    /l1 [0 bytes]
                /root [0 bytes]
                    /l1 [0 bytes]
                        /l2 [0 bytes]
                """;
        printer.print(rootdir);
        String output = outputStream.toString();
        TestCase.assertEquals(expected,output);
    }

    @Test
    public void testLimitOption(){
        JduPrinter printer = new JduPrinter(printStream,new Options(5,1,true));
        DuDirectory rootdir = new DuDirectory(Path.of("/root"));

        DuRegular f1 = new DuRegular(Path.of("root/file1"),0);
        DuRegular f2 = new DuRegular(Path.of("root/file2"),0);
        DuRegular f3 = new DuRegular(Path.of("root/file3"),0);
        DuRegular f4 = new DuRegular(Path.of("root/file4"),0);
        DuRegular f5 = new DuRegular(Path.of("root/file5"),0);
        DuRegular f6 = new DuRegular(Path.of("root/file6"),0);

        rootdir.setChildren(new ArrayList<>(List.of(f1,f2,f3,f4,f5,f6)));

        printer.print(rootdir);
        printer = new JduPrinter(printStream,new Options(5,3,true));
        printer.print(rootdir);

        String output = outputStream.toString();
        String expected = """
                /root [0 bytes]
                    file1 [0 bytes]
                /root [0 bytes]
                    file1 [0 bytes]
                    file2 [0 bytes]
                    file3 [0 bytes]
                """;
        TestCase.assertEquals(expected,output);

    }

    @Test
    public void testRegularSize(){
        JduPrinter printer = new JduPrinter(printStream,new Options(5,5,true));
        DuRegular root = new DuRegular(Path.of("/rootfile"),15);

        printer.print(root);
        String output = outputStream.toString();
        String expected = """
                rootfile [15 bytes]
                """;
        TestCase.assertEquals(expected,output);
    }

    // CR: check if options work
    // CR: check every type of file as a root
    // CR: check directories with different content
}
