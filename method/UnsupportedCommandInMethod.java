package oop.ex6.method;

/**
 * unsupported command in method block.
 *
 * @author rina.karnauch, edenkeidar
 */
public class UnsupportedCommandInMethod extends MethodException {

    /*
   informative message for an unexpected line in a method
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "unsupported command inside a method.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public UnsupportedCommandInMethod(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public UnsupportedCommandInMethod() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
