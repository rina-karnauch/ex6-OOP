package oop.ex6.method;

/**
 * class of an exception inside a method
 *
 * @author rina.karnauch, edenkeidar
 */
public abstract class MethodException extends Exception {

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String VARIABLE_EXCEPTION_MESSAGE = "Method Exception, in line: ";

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String VARIABLE_EXCEPTION_MESSAGE_NO_LINE = "Method Exception: ";

    /**
     * constructor for unsupported command exception
     *
     * @param line        line of unsupported command
     * @param informative informative message about the exception
     */
    public MethodException(int line, String informative) {
        super(VARIABLE_EXCEPTION_MESSAGE + line + ", " + informative);
    }

    /**
     * constructor for unsupported command exception
     *
     * @param informative informative message about the exception
     */
    public MethodException(String informative) {
        super(VARIABLE_EXCEPTION_MESSAGE + informative);
    }
}
