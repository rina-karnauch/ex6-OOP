package oop.ex6.variable;

/**
 * variable exception abstract class to represent an exception in variables
 */
public abstract class VariableException extends Exception {

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String VARIABLE_EXCEPTION_MESSAGE = "Variable Exception, in line: ";

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String VARIABLE_EXCEPTION_MESSAGE_NO_LINE = "Variable Exception: ";

    /**
     * constructor for unsupported command exception
     *
     * @param line        line of unsupported command
     * @param informative informative message about the exception
     */
    public VariableException(int line, String informative) {
        super(VARIABLE_EXCEPTION_MESSAGE + line + ", " + informative);
    }

    /**
     * constructor for unsupported command exception
     *
     * @param informative informative message about the exception
     */
    public VariableException(String informative) {
        super(VARIABLE_EXCEPTION_MESSAGE + informative);
    }
}
