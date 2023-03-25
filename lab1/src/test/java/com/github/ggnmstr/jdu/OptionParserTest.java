package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.exception.DuParserException;
import junit.framework.TestCase;
import org.junit.Test;

public class OptionParserTest {
    @Test
    public void testDefaultParameters() throws DuParserException {
        String[] args = {"/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(new Options(5,5,false),options);
    }

    @Test
    public void testSymlinkKey() throws DuParserException {
        String[] args = {"-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(new Options(5,5,true),options);
    }

    @Test
    public void testDepth() throws DuParserException {
        String[] args = {"--depth","10","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(new Options(10,5,false),options);
    }

    @Test
    public void testLimit() throws DuParserException {
        String[] args = {"--limit","10","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(new Options(5,10,false),options);
    }

    @Test
    public void testAllWithPermutations() throws DuParserException {
        Options excepted = new Options(7,2,true);

        String[] args = {"--limit","2","--depth","7","-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
        TestCase.assertEquals(excepted,options);

        args = new String[]{"--depth", "7", "--limit", "2", "-L", "/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertEquals(excepted,options);

        args = new String[]{"--depth","7","-L", "--limit","2","/foo/bar"};
        options = OptionsParser.parseParams(args);
        TestCase.assertEquals(excepted,options);
    }

    @Test(expected = DuParserException.class)
    public void testInvalidNumber() throws DuParserException {
        String[] args = {"--limit","-1","--depth","7","-L","/foo/bar"};
        Options options = OptionsParser.parseParams(args);
    }

    @Test(expected = DuParserException.class)
    public void testInvalidNumber2() throws DuParserException {
        String[] args = new String[]{"--limit", "10", "--depth", "-1", "-L", "/foo/bar"};
        Options options = OptionsParser.parseParams(args);
    }

    @Test(expected = DuParserException.class)
    public void testInvalidKey() throws DuParserException {
        String[] args = new String[]{"--limit", "10", "--depth", "7", "-fake", "/foo/bar"};
        Options options = OptionsParser.parseParams(args);
    }


    @Test(expected = DuParserException.class)
    public void testWrongOrder() throws DuParserException {
        String[] args = {"/foo/bar","--limit","-1","--depth","7","-L"};
        Options options = OptionsParser.parseParams(args);
    }
}
