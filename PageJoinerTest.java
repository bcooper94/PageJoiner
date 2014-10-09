

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.StringReader;
import java.io.Reader;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintStream;

/**
 * The test class PageJoinerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PageJoinerTest
{
    /**
     * Default constructor for test class PageJoinerTest
     */
    public PageJoinerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test parseFiles method.
     */
    @Test
    public void parseFileT()
    {
        // strReader1: expect lines of interest to be: 1, 3.
        StringReader strReader1 = new StringReader(
            "merge this line\nWith this one.\nDon't merge this!\n"
            + "But merge this one\nwith this line?");
        StringReader strReader2 = new StringReader(
            "No mergers in this entire file.\n"
            + "None at all?\nZero\"\nExpect zero merges!");
        StringReader strReader3 = new StringReader(
            "Merge\nEvery\nLine\nIn\nThis\nFile");
        Scanner strScan1 = new Scanner(strReader1);
        Scanner strScan2 = new Scanner(strReader2);
        Scanner strScan3 = new Scanner(strReader3);
        PageJoiner pj1 = new PageJoiner();
        PageJoiner pj2 = new PageJoiner();
        PageJoiner pj3 = new PageJoiner();
        List<Integer> candidates1 = null;
        ArrayList<String> lines1 = null;
        Vector<String> lineStrings1 = null;
        List<Integer> candidates2 = null;
        ArrayList<String> lines2 = null;
        Vector<String> lineStrings2 = null;
        List<Integer> candidates3 = null;
        ArrayList<String> lines3 = null;
        Vector<String> lineStrings3 = null;
        
        candidates1 = pj1.getCandidates(strReader1);
        lineStrings1 = pj1.formatJListLines();
        //lines1 = pj1.parseFile(strReader1);
        
        candidates2 = pj2.getCandidates(strReader2);
        lineStrings2 = pj2.formatJListLines();
        //lines2 = pj2.parseFile(strReader2);
        
        candidates3 = pj3.getCandidates(strReader3);
        lineStrings3 = pj3.formatJListLines();
        //lines3 = pj3.parseFile(strReader3);
        
        // getCandidates tests:
        assertEquals(candidates1.size(), 2);
        assertEquals((int) candidates1.get(0), 1);
        assertEquals((int) candidates1.get(1), 4);
        assertTrue(candidates2.isEmpty());
        assertEquals((int) candidates3.size(), 5);
        assertEquals((int) candidates3.get(0), 1);
        assertEquals((int) candidates3.get(1), 2);
        assertEquals((int) candidates3.get(2), 3);
        assertEquals((int) candidates3.get(3), 4);
        assertEquals((int) candidates3.get(4), 5);
        
        // formatJListLines tests:
        assertEquals(lineStrings1.size(), 2);
        assertEquals(lineStrings1.get(0),
            "0001: merge this line             With this one.");
        assertEquals(lineStrings1.get(1),
            "0004: But merge this one          with this line?");
        assertTrue(lineStrings2.isEmpty());
        assertEquals(lineStrings3.size(), 5);
        assertEquals(lineStrings3.get(0)
            , "0001: Merge                       Every");
        assertEquals(lineStrings3.get(1)
            , "0002: Every                       Line");
        assertEquals(lineStrings3.get(2)
            , "0003: Line                        In");
        assertEquals(lineStrings3.get(3)
            , "0004: In                          This");
        assertEquals(lineStrings3.get(4)
            , "0005: This                        File");
        
        // parseFile tests:
        /*
        assertEquals(lines1.size(), 2);
        assertEquals(lines1.get(0), "merge this line");
        assertEquals(lines1.get(1), "But merge this one");
        assertTrue(lines2.isEmpty());
        assertEquals(lines3.size(), 6);
        assertEquals(lines3.get(0), "Merge");
        assertEquals(lines3.get(1), "Every");
        assertEquals(lines3.get(2), "Line");
        assertEquals(lines3.get(3), "In");
        assertEquals(lines3.get(4), "This");
        assertEquals(lines3.get(5), "File");
        */
    }

    
    /**
     * Test joinLines method.
     */
    @Test
    public void joinLinesT()
    {
        // strReader1: expect lines of interest to be: 1, 3.
        StringReader strReader1 = new StringReader(
            "merge this line\nWith this one.\nDon't merge this!"
            + "\nBut merge this one\nwith this line?");
        StringReader strReader2 = new StringReader(
            "No mergers in this entire file.\n"
            + "None at all?\nZero\"\nExpect zero merges!");
        StringReader strReader3 = new StringReader(
            "Merge\nEvery\nLine\nIn\nThis\nFile");
        StringReader strReader4 = new StringReader(
            "Hyphen-\ntest!\nMake-\nsure hyphens work.");
        StringReader strReader5 = new StringReader(
            "Testing to\nfind a pattern in errors.\n"
            + "Line3\nwith line4.\nLine5!\nLine6\n"
            + "plus line7?\nline8\nand line9.\nline10 and\nline 11.");
        ArrayList<String> postMerge1 = new ArrayList<String>();
        ArrayList<String> postMerge2 = new ArrayList<String>();
        ArrayList<String> postMerge3 = new ArrayList<String>();
        ArrayList<String> postMerge4 = new ArrayList<String>();
        ArrayList<String> postMerge5 = new ArrayList<String>();
        ArrayList<String> postMerge6 = new ArrayList<String>();

        PageJoiner pj1 = new PageJoiner();
        PageJoiner pj2 = new PageJoiner();
        PageJoiner pj3 = new PageJoiner();
        PageJoiner pj4 = new PageJoiner();
        PageJoiner pj5 = new PageJoiner();
        
        List<Integer> candidates1 = null;
        Vector<String> lineStrings1 = null;
        List<Integer> candidates2 = null;
        Vector<String> lineStrings2 = null;
        List<Integer> candidates3 = null;
        Vector<String> lineStrings3 = null;
        List<Integer> candidates4 = null;
        Vector<String> lineStrings4 = null;
        List<Integer> candidates5 = null;
        Vector<String> lineStrings5 = null;
        
        candidates1 = pj1.getCandidates(strReader1);
        lineStrings1 = pj1.formatJListLines();
        
        candidates2 = pj2.getCandidates(strReader2);
        lineStrings2 = pj2.formatJListLines();
        
        candidates3 = pj3.getCandidates(strReader3);
        lineStrings3 = pj3.formatJListLines();
        
        candidates4 = pj4.getCandidates(strReader4);
        lineStrings4 = pj4.formatJListLines();
        
        candidates5 = pj5.getCandidates(strReader5);
        lineStrings5 = pj5.formatJListLines();
        
        // Arrays of selected indices in GUI:
        int[] mergerInput1 = {};
        int[] mergerInput2 = {};
        int[] mergerInput4 = {0};
        int[] mergerInput5 = {1, 2, 4};
        
        // Use fake GUI input to determine which candidates to skip
        pj1.getMergerInput(mergerInput1);
        pj2.getMergerInput(mergerInput2);
        pj4.getMergerInput(mergerInput4);
        pj5.getMergerInput(mergerInput5);
        
        postMerge1 = pj1.joinLines(false);
        postMerge2 = pj2.joinLines(true);
        postMerge3 = pj3.joinLines(false);
        postMerge4 = pj4.joinLines(false);
        postMerge5 = pj4.joinLines(true);
        postMerge6 = pj5.joinLines(true);
        
        // joinLines tests:
        assertEquals(postMerge1.get(0), "merge this line With this one.");
        assertEquals(postMerge1.get(1), "Don't merge this!");
        assertEquals(postMerge1.get(2), "But merge this one with this line?");
        //assertTrue(postMerge2.size() == 0);
        assertEquals(postMerge3.get(0), "Merge Every Line In This File");
        assertEquals(postMerge4.get(0), "Hyphen-");
        assertEquals(postMerge4.get(1), "test!");
        assertEquals(postMerge4.get(2), "Makesure hyphens work.");
        assertEquals(postMerge5.get(0), "Hyphen-");
        assertEquals(postMerge5.get(1), "test!");
        assertEquals(postMerge5.get(2), "Make-sure hyphens work.");
        assertEquals(postMerge6.size(), 9);
        assertEquals(postMerge6.get(0), "Testing to find a pattern in errors.");
        assertEquals(postMerge6.get(1), "Line3");
        assertEquals(postMerge6.get(2), "with line4.");
        assertEquals(postMerge6.get(3), "Line5!");
        assertEquals(postMerge6.get(4), "Line6");
        assertEquals(postMerge6.get(5), "plus line7?");
        assertEquals(postMerge6.get(6), "line8 and line9.");
        assertEquals(postMerge6.get(7), "line10 and");
        assertEquals(postMerge6.get(8), "line 11.");
    }

    
    /**
     * Test writeToFile method.
     */
    @Test
    public void writeToFileT()
    {
        StringReader strReader1 = new StringReader(
            "merge this line\nWith this one.\nDon't merge this!"
            + "\nBut merge this one\nwith this line?");
        StringReader strReader2 = new StringReader(
            "No mergers in this entire file.\n"
            + "None at all?\nZero\"\nExpect zero merges!");
        StringReader strReader3 = new StringReader(
            "Testing to\nfind a pattern in errors.\nLine3\nwith line4."
            + "\nLine5!\nLine6\nplus line7?\nline8\nand line9.\nline10 and\nline 11.");
        StringReader strReader4 = new StringReader(
            "Hyphen-\ntest!\nMake-\nsure hyphens work.");
        PageJoiner pj1 = new PageJoiner();
        PageJoiner pj2 = new PageJoiner();
        PageJoiner pj3 = new PageJoiner();
        PageJoiner pj4 = new PageJoiner();
        Scanner strScan1 = new Scanner(strReader1);
        Scanner strScan2 = new Scanner(strReader2);
        Scanner strScan3 = new Scanner(strReader3);
        Scanner strScan4 = new Scanner(strReader4);
        ArrayList<String> postMerge1 = new ArrayList<String>();
        ArrayList<String> postMerge2 = new ArrayList<String>();
        ArrayList<String> postMerge3 = new ArrayList<String>();
        ArrayList<String> postMerge4 = new ArrayList<String>();
        // Scanners to verify output:
        Scanner verifyOutput1 = null;
        Scanner verifyOutput2 = null;
        Scanner verifyOutput3 = null;
        Scanner verifyOutput4 = null;
        // Arrays of selected indices in GUI:
        int[] mergerInput1 = {1};
        int[] mergerInput2 = {};
        int[] mergerInput3 = {0, 1, 2, 3, 4};
        int[] mergerInput4 = {0};
        try
        {
            verifyOutput1 = new Scanner(new File("book.txt"));
            verifyOutput2 = new Scanner(new File("book.txt"));
            verifyOutput3 = new Scanner(new File("book.txt"));
            verifyOutput4 = new Scanner(new File("book.txt"));
        }
        catch (FileNotFoundException excep)
        {
            System.out.println("Cannot find \"book.txt\"");
        }
        
        pj1.getCandidates(strReader1);
        pj2.getCandidates(strReader2);
        pj3.getCandidates(strReader3);
        pj4.getCandidates(strReader4);
        
        
        pj1.getMergerInput(mergerInput1);
        pj2.getMergerInput(mergerInput2);
        pj3.getMergerInput(mergerInput3);
        pj4.getMergerInput(mergerInput4);
        
        
        postMerge1 = pj1.joinLines(false);
        postMerge2 = pj2.joinLines(true);
        postMerge3 = pj3.joinLines(true);
        postMerge4 = pj4.joinLines(true);
        
        pj1.writeToFile(postMerge1);
        
        assertEquals(verifyOutput1.nextLine(), "merge this line With this one.");
        assertEquals(verifyOutput1.nextLine(), "Don't merge this!");
        assertEquals(verifyOutput1.nextLine(), "But merge this one");
        assertEquals(verifyOutput1.nextLine(), "with this line?");
        
        pj2.writeToFile(postMerge2);
        
        assertEquals(verifyOutput2.nextLine(), "No mergers in this entire file.");
        assertEquals(verifyOutput2.nextLine(), "None at all?");
        assertEquals(verifyOutput2.nextLine(), "Zero\"");
        assertEquals(verifyOutput2.nextLine(), "Expect zero merges!");
        
        pj3.writeToFile(postMerge3);
        
        assertEquals(verifyOutput3.nextLine(), "Testing to");
        assertEquals(verifyOutput3.nextLine(), "find a pattern in errors.");
        assertEquals(verifyOutput3.nextLine(), "Line3");
        assertEquals(verifyOutput3.nextLine(), "with line4.");
        assertEquals(verifyOutput3.nextLine(), "Line5!");
        assertEquals(verifyOutput3.nextLine(), "Line6");
        assertEquals(verifyOutput3.nextLine(), "plus line7?");
        assertEquals(verifyOutput3.nextLine(), "line8");
        assertEquals(verifyOutput3.nextLine(), "and line9.");
        assertEquals(verifyOutput3.nextLine(), "line10 and");
        assertEquals(verifyOutput3.nextLine(), "line 11.");
        
        pj4.writeToFile(postMerge4);
        
        assertEquals(verifyOutput4.nextLine(), "Hyphen-");
        assertEquals(verifyOutput4.nextLine(), "test!");
        assertEquals(verifyOutput4.nextLine(), "Make-sure hyphens work.");
    }

    
    /**
     * Test getAllLines method.
     */
    @Test
    public void getAllLinesT()
    {
        PageJoiner pj1 = new PageJoiner();
        StringReader strReader1 = new StringReader(
            "Testing to\nfind a pattern in errors.\nLine3\nwith line4."
            +"\nLine5!\nLine6\nplus line7?\nline8\nand line9.\nline10 and\nline 11.");
        Scanner scan1 = new Scanner(strReader1);
        ArrayList<String> allLines = null;
        
        pj1.getCandidates(strReader1);
        
        allLines = pj1.getAllLines();
        
        assertEquals(allLines.get(0), "Testing to");
        assertEquals(allLines.get(1), "find a pattern in errors.");
        assertEquals(allLines.get(2), "Line3");
        assertEquals(allLines.get(3), "with line4.");
        assertEquals(allLines.get(4), "Line5!");
        assertEquals(allLines.get(5), "Line6");
        assertEquals(allLines.get(6), "plus line7?");
        assertEquals(allLines.get(7), "line8");
        assertEquals(allLines.get(8), "and line9.");
        assertEquals(allLines.get(9), "line10 and");
        assertEquals(allLines.get(10), "line 11.");
    }

    
    /**
     * Test padWithZeros method.
     */
    @Test
    public void padWithZerosT()
    {
        PageJoiner pj1 = new PageJoiner();
        int[] lineNums = new int[9999];
        
        // Populate lineNums array for testing each lineNum
        for (int index = 0; index < 9999; index++)
        {
            lineNums[index] = index;
        }
        
        // Test each value for correct padding
        for (int line : lineNums)
        {
            // If line is 1 digit
            if (line < 10)
            {
                assertEquals(pj1.padWithZeros(line), "000");
            }
            // If line is 2 digits
            else if (line < 100)
            {
                assertEquals(pj1.padWithZeros(line), "00");
            }
            // If line is 3 digits
            else if (line < 1000)
            {
                assertEquals(pj1.padWithZeros(line), "0");
            }
            // If line is 4 digits
            else
            {
                assertEquals(pj1.padWithZeros(line), "");
            }
        }
    }

    
    /**
     * Test getMergerInput method.
     */
    @Test
    public void mgInpt()
    {
        PageJoiner pj1 = new PageJoiner();
        StringReader strReader1 = new StringReader(
            "Testing to\nfind a pattern in errors.\nLine3\nwith line4.\n"
            + "Line5!\nLine6\nplus line7?\nline8\nand line9.\nline10 and\nline 11.");
        List<Integer> getLinesToMerge1 = null;
        List<Integer> getLinesToMerge2 = null;
        List<Integer> getLinesToMerge3 = null;
        boolean[] toBeMerged1 = {false, false, false, true, true};
        boolean[] toBeMerged2 = {true, true, true, false, false};
        boolean[] toBeMerged3 = {true, true, true, true, true};
        int[] mergerInput1 = {0, 1, 2};
        int[] mergerInput2 = {3, 4};
        int[] mergerInput3 = {};
        
        getLinesToMerge1 = pj1.getCandidates(strReader1);
        pj1.getMergerInput(mergerInput1);
        
        assertEquals(getLinesToMerge1.size(), 5);
        assertEquals((int) getLinesToMerge1.get(0), 1);
        assertEquals((int) getLinesToMerge1.get(1), 3);
        assertEquals((int) getLinesToMerge1.get(2), 6);
    }
    
    /**
     * Test run method.
     */
    @Test
    public void run()
    {
        StringReader strReader1 = new StringReader(
            "merge this line\nWith this one.\nDon't merge this!"
            + "\nBut merge this one\nwith this line?");
        StringReader strReader2 = new StringReader(
            "No mergers in this entire file.\n"
            + "None at all?\nZero\"\nExpect zero merges!");
        StringReader strReader3 = new StringReader(
            "Testing to\nfind a pattern in errors.\nLine3\n"
            + "with line4.\nLine5!\nLine6\nplus line7?\n"
            + "line8\nand line9.\nline10 and\nline 11.");
        StringReader strReader4 = new StringReader(
            "Hyphen-\ntest!\nMake-\nsure hyphens work.");
            /*
        StringReader skipfile1 = new StringReader("1\nblah blah4");
        StringReader skipfile2 = new StringReader("test1\ntes25t");
        StringReader skipfile3 = new StringReader("4\n3\n2\n1\n");
        StringReader skipfile4 = new StringReader("fourtytwo\n0\n");
        */
        PageJoiner pj1 = new PageJoiner();
        PageJoiner pj2 = new PageJoiner();
        PageJoiner pj3 = new PageJoiner();
        PageJoiner pj4 = new PageJoiner();
        // Scanners to verify output:
        Scanner verifyOutput1 = null;
        Scanner verifyOutput2 = null;
        Scanner verifyOutput3 = null;
        Scanner verifyOutput4 = null;
        // Arrays of selected indices in GUI:
        int[] mergerInput1 = {1};
        int[] mergerInput2 = {};
        int[] mergerInput3 = {0, 1, 2, 3, 4};
        int[] mergerInput4 = {0};
        try
        {
            verifyOutput1 = new Scanner(new File("book.txt"));
            verifyOutput2 = new Scanner(new File("book.txt"));
            verifyOutput3 = new Scanner(new File("book.txt"));
            verifyOutput4 = new Scanner(new File("book.txt"));
        }
        catch (FileNotFoundException excep)
        {
            System.out.println("Cannot find \"book.txt\"");
        }
        
        pj1.run(strReader1);
        /*
        assertEquals(verifyOutput1.nextLine(), "merge this line With this one.");
        assertEquals(verifyOutput1.nextLine(), "Don't merge this!");
        assertEquals(verifyOutput1.nextLine(), "But merge this one with this line?");
        */
        pj2.run(strReader2);
        /*
        assertEquals(verifyOutput2.nextLine(), "No mergers in this entire file.");
        assertEquals(verifyOutput2.nextLine(), "None at all?");
        assertEquals(verifyOutput2.nextLine(), "Zero\"");
        assertEquals(verifyOutput2.nextLine(), "Expect zero merges!");
        */
        pj3.run(strReader3);
        /*
        assertEquals(verifyOutput3.nextLine(), "Testing to find a pattern in errors.");
        assertEquals(verifyOutput3.nextLine(), "Line3 with line4.");
        assertEquals(verifyOutput3.nextLine(), "Line5!");
        assertEquals(verifyOutput3.nextLine(), "Line6 plus line7?");
        assertEquals(verifyOutput3.nextLine(), "line8 and line9.");
        assertEquals(verifyOutput3.nextLine(), "line10 and line 11.");
        */
        pj4.run(strReader4);
        /*
        assertEquals(verifyOutput4.nextLine(), "Hyphentest!");
        assertEquals(verifyOutput4.nextLine(), "Makesure hyphens work.");
        */
    }
    
    /**
     * Test parseSkipFile method.
     */
    @Test
    public void skip()    
    {
        PageJoiner pj1 = new PageJoiner();
        PageJoiner pj2 = new PageJoiner();
        PageJoiner pj3 = new PageJoiner();
        StringReader lines1 = new StringReader(
            "line1\nline2\nline3\n4\n5\n6\n7\n8\n"
            + "9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20");
        StringReader lines2 = new StringReader(
            "line1\nline2\nline3\n4\n5\n6\n7\n8"
            + "\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18");
        StringReader lines3 = new StringReader(
            "line1\nline2\nline3\n4\n5\n6\n7"
            + "\n8\n9\n10\n11\n12\n13\n14\n15\n16");
        int[] mergeLines1 = {4, 9, 13, 14};
        int[] mergeLines2 = {3, 6, 8, 12};
        int[] mergeLines3 = {2, 3, 4};
        int mergeCount = 0;
        
        // skipfile1 lines: 14, 13, 9, 4
        StringReader skipfile1 = new StringReader(
            "14\n13\n9\nImproperly formatted line\nBlah6Blah\n4");
        // skipfile2 lines: 12, 8, 6, 3
        StringReader skipfile2 = new StringReader("12\n8\n6\nNope\nNo24\n3\n42\n21");
        // skipfile3 lines: 4, 3, 2, 0
        StringReader skipfile3 = new StringReader("4\n\n3\n11\nFour4\n2\n0");
        ArrayList<Integer> getLinesToMerge1 = null;
        ArrayList<Integer> getLinesToMerge2 = null;
        ArrayList<Integer> getLinesToMerge3 = null;
        
        pj1.getCandidates(lines1);
        pj1.parseSkipfile(skipfile1);
        getLinesToMerge1 = pj1.getSelectedLines();
        
        pj2.getCandidates(lines2);
        pj2.parseSkipfile(skipfile2);
        getLinesToMerge2 = pj2.getSelectedLines();
        
        pj3.getCandidates(lines3);
        pj3.parseSkipfile(skipfile3);
        getLinesToMerge3 = pj3.getSelectedLines();
        
        // Test each value of toBeMerged1
        assertEquals((int) getLinesToMerge1.get(0), 1);
        assertEquals((int) getLinesToMerge1.get(1), 2);
        assertEquals((int) getLinesToMerge1.get(2), 3);
        assertEquals((int) getLinesToMerge1.get(3), 4);
        assertEquals((int) getLinesToMerge1.get(4), 6);
        assertEquals((int) getLinesToMerge1.get(5), 7);
        assertEquals((int) getLinesToMerge1.get(6), 8);
        assertEquals((int) getLinesToMerge1.get(7), 9);
        assertEquals((int) getLinesToMerge1.get(8), 11);
        assertEquals((int) getLinesToMerge1.get(9), 12);
        assertEquals((int) getLinesToMerge1.get(10), 13);
        assertEquals((int) getLinesToMerge1.get(11), 16);
        assertEquals((int) getLinesToMerge1.get(12), 17);
        assertEquals((int) getLinesToMerge1.get(13), 18);
        assertEquals((int) getLinesToMerge1.get(14), 19);
        
        // Test each value of toBeMerged2
        assertEquals((int) getLinesToMerge2.get(0), 1);
        assertEquals((int) getLinesToMerge2.get(1), 2);
        assertEquals((int) getLinesToMerge2.get(2), 3);
        assertEquals((int) getLinesToMerge2.get(3), 5);
        assertEquals((int) getLinesToMerge2.get(4), 6);
        assertEquals((int) getLinesToMerge2.get(5), 8);
        assertEquals((int) getLinesToMerge2.get(6), 10);
        assertEquals((int) getLinesToMerge2.get(7), 11);
        assertEquals((int) getLinesToMerge2.get(8), 12);
        assertEquals((int) getLinesToMerge2.get(9), 14);
        assertEquals((int) getLinesToMerge2.get(10), 15);
        assertEquals((int) getLinesToMerge2.get(11), 16);
        assertEquals((int) getLinesToMerge2.get(12), 17);
        
        // Test each value of toBeMerged3
        assertEquals((int) getLinesToMerge3.get(0), 2);
        assertEquals((int) getLinesToMerge3.get(1), 6);
        assertEquals((int) getLinesToMerge3.get(2), 7);
        assertEquals((int) getLinesToMerge3.get(3), 8);
        assertEquals((int) getLinesToMerge3.get(4), 9);
        assertEquals((int) getLinesToMerge3.get(5), 10);
        assertEquals((int) getLinesToMerge3.get(6), 11);
        assertEquals((int) getLinesToMerge3.get(7), 12);
        assertEquals((int) getLinesToMerge3.get(8), 13);
        assertEquals((int) getLinesToMerge3.get(9), 14);
        assertEquals((int) getLinesToMerge3.get(10), 15);
    }
}