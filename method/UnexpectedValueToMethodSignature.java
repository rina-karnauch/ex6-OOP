package oop.ex6.method;

/**
 * unsupported type given to method signature exception
 *
 * @author edenkeider, rina.karnauch
 */
public class UnexpectedValueToMethodSignature extends MethodException {

    /*
   informative message for a missing comma at end of line
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "unexpected value given to method signature.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public UnexpectedValueToMethodSignature(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public UnexpectedValueToMethodSignature() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
