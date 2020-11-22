package oop.ex6.parsing;

/**
 * class of an unknown line format in our sjava file
 *
 * @author rina.karnauch, edenkeidar
 */
public class UnknownLineFormat extends Exception {

    /*
    informative message for the current exception of unknown line format in sjava file, first part of msg.
     */
    private static final String EXCEPTION_ERROR_BEGINNING = "unfamiliar command format in line: ";

    /*
   informative message for the current exception of unknown line format in sjava file, secon d part of msg.
    */
    private static final String EXCEPTION_ERROR_ENDING = " in file.";

    /**
     * constructor for our unknown line format exception.
     *
     * @param line line number where format is unknown in sjava file.
     */
    public UnknownLineFormat(int line) {
        super(EXCEPTION_ERROR_BEGINNING + line + EXCEPTION_ERROR_ENDING);
    }
}
