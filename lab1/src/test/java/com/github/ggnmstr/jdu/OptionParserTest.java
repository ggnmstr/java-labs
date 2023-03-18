package com.github.ggnmstr.jdu;

import junit.framework.TestCase;
import org.junit.Test;

public class OptionParserTest {
    @Test
    public void testDefaultParameters() throws ParserException {
        String[] args = {"/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,new Options(5,5,false));
    }

    @Test
    public void testSymlinkKey() throws ParserException {
        String[] args = {"-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,new Options(5,5,true));
    }

    @Test
    public void testDepth() throws ParserException {
        String[] args = {"--depth","10","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,new Options(10,5,false));
    }

    @Test
    public void testLimit() throws ParserException {
        String[] args = {"--limit","10","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,new Options(5,10,false));
    }

    @Test
    public void testAllWithPermutations() throws ParserException {
        Options excepted = new Options(7,2,true);

        String[] args = {"--limit","2","--depth","7","-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,excepted);

        args = new String[]{"--depth", "7", "--limit", "2", "-L", "/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,excepted);

        args = new String[]{"--depth","7","-L", "--limit","2","/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertEquals(options,excepted);
    }

    @Test
    public void testWrongParameters() throws ParserException {
        String[] args = {"--limit","-1","--depth","7","-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertNull(options);

        args = new String[]{"--limit", "10", "--depth", "-1", "-L", "/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertNull(options);

        args = new String[]{"--limit", "10", "--depth", "7", "-fake", "/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertNull(options);
    }

    @Test
    public void testWrongOrder() throws ParserException {
        String[] args = {"/foo/bar","--limit","-1","--depth","7","-L"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertNull(options);
    }
}
