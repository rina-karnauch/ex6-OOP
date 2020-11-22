package oop.ex6.verifier;

/**
 * abstract class of syntax errors in sjava conformer code
 *
 * @author rina.karnauch, edenkeidarS
 */
public abstract class SyntaxError extends Exception {

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String SYNTAX_ERROR_MESSAGE = "Syntax Error in line: ";


    /*
     * abstract added error message before informative line about the exception
     */
    private final static String SYNTAX_ERROR_MESSAGE_NO_LINE = "Syntax Error: ";

    /**
     * a syntax error constructor of exception, with line number.
     *
     * @param line        number of line of exception
     * @param informative informative message about exception
     */
    public SyntaxError(int line, String informative) {
        super(SYNTAX_ERROR_MESSAGE + line + "," + informative);
    }

    /**
     * constructor for unsupported command exception, without line number.
     *
     * @param informative informative message about the exception
     */
    public SyntaxError(String informative) {
        super(SYNTAX_ERROR_MESSAGE_NO_LINE + informative);
    }
}
