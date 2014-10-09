import java.io.File;
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.util.Vector;
import java.io.StringReader;
import java.io.PrintStream;
import java.io.FileNotFoundException;
/**
 * Eliminate unneeded pagebreaks in a text file.
 * 
 * @author Brandon Cooper 
 * @version Fall 2014
 */
public class PageJoiner
{
    private static final int kMaxLines = 10000;
    private static final int kCheckNumPlaces = 10;
    private ArrayList<Integer> selectedLineNums;
    private ArrayList<String> allLines;

    /**
     * Create a PageJoiner to join pages.
     */
    public PageJoiner()
    {
        selectedLineNums = new ArrayList<Integer>();
        allLines = new ArrayList<String>();
    }

    /**
     * Get the candidate lines to be merged.
     * @param rdr Reader to parse file input.
     * @return candidate line numbers
     */
    public List<Integer> getCandidates(Reader rdr)
    {
        Scanner lineRdr = new Scanner(rdr);
        String currentLine = null;
        int curLineNum = 1;
        ArrayList<Integer> allCandidates = null;

        // Scan for lines of interest
        while (lineRdr.hasNextLine())
        {
            currentLine = lineRdr.nextLine();
            allLines.add(currentLine);

            // Check if the line ends with a punctuation
            if (currentLine.isEmpty()
                || !(currentLine.charAt(
                        currentLine.length() - 1) == '.'
                    || currentLine.charAt(
                        currentLine.length() - 1) == '!'
                    || currentLine.charAt(
                        currentLine.length() - 1) == '?'
                    || currentLine.charAt(
                        currentLine.length() - 1) == '"'))
            {
                selectedLineNums.add(curLineNum);
            }
           
            curLineNum++;
        }

        allCandidates = new ArrayList<Integer>(selectedLineNums);

        // Remove last candidate if at final line
        if (!selectedLineNums.isEmpty()
            && selectedLineNums.get(selectedLineNums.size() - 1)
            == allLines.size())
        {
            allCandidates.remove(selectedLineNums.size() - 1);
        }

        return allCandidates;
    }

    /**
     * Accessor for allLines.
     * @return ArrayList all scanned lines
     */
    public ArrayList<String> getAllLines()
    {
        return allLines;
    }

    /**
     * Accessor for selectedCandidateNums.
     * @return ArrayList of all candidate lines to be merged.
     */
    public ArrayList<Integer> getSelectedLines()
    {
        return selectedLineNums;
    }

    /**
     * Merge candidate lines with following lines.
     * @param retainHyphens Whether or not hyphens should be retained in candidate lines
     * @return ArrayList of all lines in book after lines have been merged.
     */
    public ArrayList<String> joinLines(boolean retainHyphens)
    {
        ArrayList<String> linesAfterMerge = new ArrayList<String>();
        String currentLine = null;
        int totalLines = allLines.size();
        int linesToBeJoined = selectedLineNums.size();
        int mergeCount = 0;

        // Iterate through all lines, merging candidates
        for (int index = 0; index < totalLines; index++)
        {
            // if line is a candidate, merge it
            if (mergeCount < selectedLineNums.size()
                && index == selectedLineNums.get(mergeCount) - 1)
            {
                currentLine = formatBeforeJoin(
                    allLines.get(selectedLineNums.get(mergeCount) - 1),
                    mergeCount, retainHyphens);

                // Convert empty lines to spaces
                if (currentLine == null)
                {
                    currentLine = " ";
                }
                
                // Merge all consecutive candidates
                while (mergeCount + 1 < selectedLineNums.size()
                    && selectedLineNums.get(mergeCount)
                    == (selectedLineNums.get(mergeCount + 1) - 1))
                {
                    currentLine += formatBeforeJoin(
                        allLines.get(selectedLineNums.get(mergeCount + 1) - 1),
                        mergeCount + 1, retainHyphens);
                    index++;
                    mergeCount++;
                }

                // If there are still lines after currentLine
                if (index + 1 < allLines.size())
                {
                    currentLine += allLines.get(index + 1);
                    mergeCount++;
                    index++;
                }
                
                linesAfterMerge.add(currentLine);
            }
            // if line is not a candidate, add it to linesAfterMerge
            else
            {
                linesAfterMerge.add(allLines.get(index));
            }
        }

        return linesAfterMerge;
    }

    /**
     * Add needed spaces and remove hyphens to a line.
     * @param line The line to be edited.
     * @param index Index of the line in selectedLineNums
     * @param retainHyphens Whether to retain hyphens or not
     */
    private String formatBeforeJoin(
        String line, int mergeCount, boolean retainHyphens)
    {
        String newLine = line;
        // If no hyphen at the end of currentLine and not last line in file
        if (line.length() > 0
            && selectedLineNums.get(mergeCount) < allLines.size()
            && allLines.get(selectedLineNums.get(mergeCount) - 1).charAt(
                line.length() - 1) != '-')
        {
            newLine += " ";
        }
        // If removing hyphens
        else if (selectedLineNums.get(mergeCount) < allLines.size()
            && !retainHyphens)
        {
            newLine = removeHyphen(newLine);
        }

        return newLine;
    }

    /**
     * Remove an ending hyphen from a String.
     * @param line Line for hyphen to be removed from
     * @return String without ending hyphen
     */
    private String removeHyphen(String line)
    {
        String removedHyphen = null;

        // If line is not empty
        if (line.length() > 0)
        {
            removedHyphen = line.substring(0, line.length() - 1);
        }

        return removedHyphen;
    }

    /**
     * Write text with removed pagebreaks to book.txt.
     * @param joinedLines ArrayList of all lines that were joined
     */
    public void writeToFile(ArrayList<String> joinedLines)
    {
        PrintStream bookWriter = null;
        int numMergedLines = 0;
        int curLineNum = 1;

        try
        {
            bookWriter = new PrintStream(new File("book.txt"));
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("Unable to create file \"book.txt\"");
        }

        // Print all lines to book.txt
        for (String line : joinedLines)
        {
            bookWriter.println(line);
        }
    }

    /**
     * Get user input for which lines not to merge.
     * @param indices Indices of the lines to not be merged.
     */
    public void getMergerInput(int[] indices)
    {
        int linesRemoved = 0;
        // Iterate through all selected line numbers
        for (int index : indices)
        {
            selectedLineNums.remove(index - linesRemoved);
            linesRemoved++;
        }
    }

    /**
     * Format lines to be sent used to create a Dialog.
     * @return Vector of strings of all reformatted candidate lines
     */
    public Vector<String> formatJListLines()
    {
        final int charsPerLine = 25;
        String reformattedLine = null;
        int spacesMissing = 0;
        String tempLine = null;
        ArrayList<String> formattedLines = new ArrayList<String>();

        // Iterate through all candidate line numbers
        for (int lineNum : selectedLineNums)
        {
            // Check if lineNum is last line in file
            if (lineNum < allLines.size())
            {
                tempLine = allLines.get(lineNum - 1);
                spacesMissing = charsPerLine - Math.min(
                    tempLine.length(), charsPerLine);
                reformattedLine = padWithZeros(lineNum) + lineNum + ": "
                    + tempLine.substring(0, Math.min(tempLine.length(), charsPerLine));
                // If a line follows the current line
                if (lineNum < allLines.size())
                {
                    // Add equal spacing to left-justify second column in JList
                    for (int spacesAdded = 0; spacesAdded < spacesMissing; spacesAdded++)
                    {
                        reformattedLine += " ";
                    }
    
                    tempLine = allLines.get(lineNum);
                    reformattedLine += "   " + tempLine.substring(
                        0, Math.min(tempLine.length(), charsPerLine));
                }
    
                formattedLines.add(reformattedLine);
            }
        }

        return new Vector<String>(formattedLines);
    }

    /**
     * Create a 4 digit number from line by padding with zeros.
     * @param line Line number
     * @return 4 digit number padded with zeros
     */
    public String padWithZeros(int line)
    {
        String padding = "000";
        // Divide by multiples of 10 to determine padding
        for (int divisor = kCheckNumPlaces;
            (line / divisor) >= 1;
            divisor *= kCheckNumPlaces)
        {
            padding = padding.substring(0, padding.length() - 1);
        }

        return padding;
    }

    /**
     * Run the PageJoiner from just a Reader.
     * @param reader Reader for the text file being scanned.
     */
    public void run(Reader reader)
    {
        Dialog joinerGUI = null;
        File skipfile = null;
        FileReader skipFileReader = null;

        getCandidates(reader);

        try
        {
            skipfile = new File("skipfile.txt");
            // If there IS a skipfile.txt, don't create GUI
            if (skipfile.exists())
            {
                skipFileReader = new FileReader(skipfile);
                parseSkipfile(skipFileReader);
                writeToFile(joinLines(false));
            }
        }
        catch (Exception except)
        {
            System.out.println("Invalid file.");
        }
        /*
        // If there is NO skipfile.txt, create GUI
        if (!skipfile.exists())
        {
            joinerGUI = new Dialog(formatJListLines(), this);
            // Make it visible and wait  
            joinerGUI.setVisible(true);
        }
        */
    }

    /**
     * Grab input from skipfile.txt.
     * @param skipfile A Reader created from skipfile.txt
     */
    public void parseSkipfile(Reader skipfile)
    {
        int previousValidLine = kMaxLines;
        Scanner skipFileReader = null;
        Scanner singleLineReader = null;
        String singleLine = null;
        int currentLine = 0;
        int linesRemoved = 0;
        ArrayList<Integer> linesToSkip = new ArrayList<Integer>();

        try
        {
            skipFileReader = new Scanner(skipfile);
        }
        catch (Exception except)
        {
            System.out.println("Failed to scan \"skipfile.txt\"");
        }

        // Read through each line in skipfile.txt
        while (skipFileReader.hasNextLine())
        {
            singleLineReader = new Scanner(skipFileReader.nextLine());

            // If there is a line number in the current line
            if (singleLineReader.hasNextInt())
            {
                currentLine = singleLineReader.nextInt();
                // Test for negative lines and descending order of lines
                if (currentLine >= 0 && previousValidLine > currentLine)
                {
                    linesToSkip.add(currentLine);
                    previousValidLine = currentLine;
                }
            }
        }

        // Mark lines to be skipped
        for (int line : linesToSkip)
        {
            selectedLineNums.remove(line);
            linesRemoved++;
        }
    }

    /**
     * Remove the pagebreaks from a page.
     * @param args Filename in command line input
     */
    public static void main(
        String[] args)
    {
        PageJoiner joiner = new PageJoiner();
        FileReader doc = null;
        File skipfile = null;
        FileReader skipFileReader = null;
        Dialog joinerGUI = null;

        try
        {
            // Check if the file name is on the command line
            if (args.length > 0)
            {
                doc = new FileReader(new File(args[0]));
            }
            // Request file name from input dialog box
            else
            {
                doc = new FileReader(
                    new File((String) JOptionPane.showInputDialog(
                        "Filename for input text?")));
            }

            joiner.getCandidates(doc);
            skipfile = new File("skipfile.txt");
            // If there IS a skipfile.txt, don't create GUI
            if (skipfile.exists())
            {
                skipFileReader = new FileReader(skipfile);
                joiner.parseSkipfile(skipFileReader);
                joiner.writeToFile(joiner.joinLines(true));
            }
        }
        catch (FileNotFoundException exception)
        {
            JOptionPane.showMessageDialog(
                null, args[0] + " not found, terminating");
            System.exit(1);
        }
        
        // If there is NO skipfile.txt, create GUI
        if (!skipfile.exists())
        {
            // Create Vector<String> to create a Dialog GUI:
            joinerGUI = new Dialog(joiner.formatJListLines(), joiner);
            // Make it visible and wait  
            joinerGUI.setVisible(true);
        }
        
    }
}
