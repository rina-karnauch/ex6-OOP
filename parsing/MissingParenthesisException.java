package oop.ex6.parsing;

/**
 * missing parenthesis exception in block of code
 *
 * @author rina.karnauch, edenkeider
 */
public class MissingParenthesisException extends Exception {

    /*
    informative message for the current exception for a missing parenthesis in code block.
     */
    private static final String EXCEPTION_ERROR = "missing parenthesis exception in block.";

    /**
     * exception of missing parenthesis constructor
     */
    public MissingParenthesisException() {
        super(EXCEPTION_ERROR);
    }

}
