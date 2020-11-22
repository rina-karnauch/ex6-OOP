package oop.ex6.method;


/**
 * missing method name exception thrown in method declaration
 *
 * @author rina.karnauch, edenkeidar
 */
public class MissingMethodName extends MethodException {

    /*
    informative message for a missing method name.
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "missing a method's correct name.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public MissingMethodName(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public MissingMethodName() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
