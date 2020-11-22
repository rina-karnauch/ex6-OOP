package oop.ex6.parsing;

/**
 * class of a read line exception
 *
 * @author rina.karnauch, edenkeidar
 */
public class ReadLineException extends IOJavaCExceptions {

    /*
    informative message for the current exception of reading line exception in readline() method, first part of msg.
     */
    private static final String EXCEPTION_ERROR_BEGINNING = "failure reading line: ";

    /*
    informative message for the current exception of reading line exception in readline() method, second part of msg.
     */
    private static final String EXCEPTION_ERROR_ENDING = " in file.";

    /**
     * a constructor for our current exception
     *
     * @param line line number where exception accrued in our sjava file reading
     */
    public ReadLineException(int line) {
        super(EXCEPTION_ERROR_BEGINNING + line + EXCEPTION_ERROR_ENDING);
    }
}
