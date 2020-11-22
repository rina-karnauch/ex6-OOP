package oop.ex6.parsing;

/**
 * class of an exception, represents an illegal parenthesis format inside a code block.
 *
 * @author edenkeidar, rina.karnauch
 */
public class IllegalParenthesisFormatException extends Exception {

    /*
    informative message for the current exception of illegal parenthesis in code block.
     */
    private static final String EXCEPTION_ERROR = "illegal parenthesis format.";

    /**
     * constructor for the exception of illegal parenthesis in code block.
     */
    public IllegalParenthesisFormatException() {
        super(EXCEPTION_ERROR);
    }

}

