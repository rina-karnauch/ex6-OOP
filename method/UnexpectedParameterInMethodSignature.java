package oop.ex6.method;

/**
 * given an unexpected parameter value to method call
 *
 * @author rina.karnauch, edenkeider
 */
public class UnexpectedParameterInMethodSignature extends MethodException {

    /*
     informative message for an unexpected parameter in method signature
     */
    private final static String INFORMATIVE_ERROR_MESSAGE = "unexpected parameter value given to methods params";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public UnexpectedParameterInMethodSignature(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public UnexpectedParameterInMethodSignature() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
