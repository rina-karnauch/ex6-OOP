
package oop.ex6.parsing;

import oop.ex6.verifier.Verifier;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * class of a parser object, which will form out a parser which will be able to check
 * code parts in our file of sjava code
 *
 * @author rina.karnauch, edenkeider
 */
public class Parser {

    /*
    line counter start point.
     */
    private final static int LINE_COUNTER_START = 1;

    /*
    buffered reader field
     */
    private BufferedReader bufferedReader;

    /*
    hash set of all function declaration lines
     */
    private ArrayList<String> declarations = new ArrayList<String>();

    /*
    array list of method block, contains an array of the code lines of the code block.
     */
    private ArrayList<ArrayList<String>> methodBlocks = new ArrayList<ArrayList<String>>();

    /*
    array list of global variables
     */
    private ArrayList<String> globalVariables = new ArrayList<String>();

    /*
    array list of global variable line declarations
     */
    private ArrayList<Integer> globalVariableLines = new ArrayList<Integer>();

    /*
    array list of method signature lines.
     */
    private ArrayList<Integer> methodLines = new ArrayList<Integer>();

    /*
    line number counter
     */
    private int lineNumber;

    /*
    current line we go through
     */
    private String currentLine;

    /**
     * constructor for the parser object
     *
     * @param fileName the file name to check the sjava code
     * @throws MissingFileException              file is missing, not found.
     * @throws MissingParenthesisException       parenthesis closer of opener are missing.
     * @throws IllegalParenthesisFormatException illegal parenthesis format.
     * @throws FileClosedException               file is closed already, but we try to close it again.
     * @throws ReadLineException                 exception in reading the line.
     * @throws UnknownLineFormat                 format of line in outer scope is illega.
     */
    public Parser(String fileName) throws MissingFileException, MissingParenthesisException,
            IllegalParenthesisFormatException, FileClosedException, ReadLineException, UnknownLineFormat {
        this.lineNumber = LINE_COUNTER_START;
        try {
            this.bufferedReader = createBuffer(fileName);
            reader();
        } catch (IOException closedFile) {
            throw new FileClosedException();
        }
    }

    /*
    method to create the buffer reader out of the file
     */
    private BufferedReader createBuffer(String fileName) throws MissingFileException, IOException {
        File sJavaFile = new File(fileName);
        FileInputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = new FileInputStream(sJavaFile);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            return bufferedReader;
        } catch (FileNotFoundException fileNotFound) {
            throw new MissingFileException();
        }
    }

    /*
    method to read the lines in the file and section them to parts.
     */
    private void reader() throws MissingParenthesisException, IllegalParenthesisFormatException, ReadLineException
            , UnknownLineFormat {
        try {
            this.currentLine = this.bufferedReader.readLine();
            while (currentLine != null) {
                checkLine(this.currentLine); // we modify line inside.
                currentLine = bufferedReader.readLine();
                this.lineNumber++;
            }
        } catch (IOException readLineFailure) { // couldn't read line.
            throw new ReadLineException(this.lineNumber);
        }

    }

    /*
    method to check the current line we go through.
     */
    private void checkLine(String inputLine) throws MissingParenthesisException, IllegalParenthesisFormatException,
            ReadLineException, UnknownLineFormat {

        Matcher commentMatcher = Verifier.COMMENT_REGEX.matcher(inputLine);
        try {
            if (!commentMatcher.matches()) {
                inputLine = inputLine.trim();
            }

            Matcher emptyLine = Verifier.EMPTY_LINE.matcher(inputLine);
            Matcher declarationMatcher = Verifier.METHOD_REGEX.matcher(inputLine);
            Matcher variableMatcher = Verifier.VARIABLE_REGEX.matcher(inputLine);
            Matcher assignmentMatcher = Verifier.ASSIGNMENT_REGEX.matcher(inputLine);

            if (declarationMatcher.matches()) {
                this.declarations.add(inputLine);
                this.methodLines.add(this.lineNumber);
                createMethodBlock();
            } else if (variableMatcher.matches() || assignmentMatcher.matches()) {
                this.globalVariables.add(inputLine);
                this.globalVariableLines.add(lineNumber);
            }
            else if (!(commentMatcher.matches()) && !(emptyLine.matches())) {
                // this is not a comment, and not a method ot variable
                // therefore unknown line format
                throw new UnknownLineFormat(this.lineNumber);
            }
        } catch (IOException readLineFailure) { // couldn't read line.
            throw new ReadLineException(this.lineNumber);
        }
    }

    /*
    method helper to create a block of code (a method block) out of strings
    inside parenthesis after a method declaration.
     */
    private void createMethodBlock() throws IllegalParenthesisFormatException, MissingParenthesisException, IOException {
        ArrayList<String> currentBlock = new ArrayList<String>();
        int pairCounter = 1;
        currentBlock.add(currentLine);
        while (pairCounter != 0) {
            currentLine = bufferedReader.readLine();
            if (currentLine == null) {
                break;
            }
            if (currentLine.contains("{") && currentLine.contains("}")) {
                throw new IllegalParenthesisFormatException();
            } else if (currentLine.contains("{")) {
                pairCounter++;
            } else if (currentLine.contains("}")) {
                pairCounter--;
            }
            if (pairCounter < 0) { // need to check correctness.
                throw new MissingParenthesisException();
            }
            currentLine = currentLine.trim();
            if (!currentLine.isEmpty()) {
                // so we don't have empty lines in our block.
                currentBlock.add(currentLine);
            }
            this.lineNumber++;
        }
        if (pairCounter != 0) {
            throw new MissingParenthesisException();
        }
        this.methodBlocks.add(currentBlock);
    }


    /**
     * method declaration object getter.
     *
     * @return hash set of all method declerations in our sjava code.
     */
    public ArrayList<String> getMethodDeclarations() {
        return this.declarations;
    }

    /**
     * global variables declaration getter method.
     *
     * @return array list of all global variables.
     */
    public ArrayList<String> getGlobalVariables() {
        return this.globalVariables;
    }

    /**
     * global variables declaration line numbers
     *
     * @return array list of line numbers
     */
    public ArrayList<Integer> getVariableLines() {
        return this.globalVariableLines;
    }

    /**
     * method blocks getter
     *
     * @return an array list of all the method blocks.
     */
    public ArrayList<ArrayList<String>> getMethodBlocks() {
        return this.methodBlocks;
    }

    /**
     * method getter to get method signature line declarations
     *
     * @return an array list of line numbers of method declarations, line by line
     */
    public ArrayList<Integer> getMethodSignatureLine() {
        return this.methodLines;
    }
}
