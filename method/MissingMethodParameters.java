package oop.ex6.method;

/**
 * missing parameters given to method declaration.
 *
 * @author rina.karnauch, edenkeidar
 */
public class MissingMethodParameters extends MethodException {

    /*
   informative message for a missing parameters line for a method
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "missing method's parameters.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public MissingMethodParameters(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public MissingMethodParameters() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
