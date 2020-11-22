package oop.ex6.method;

/**
 * missing return type in method declaration.
 *
 * @author rina.karnauch, edenkeidar
 */
public class MissingMethodReturnType extends MethodException {

    /*
    message of missing methods return type
     */
    private final static String INFORMATIVE_ERROR_MSG = "missing methods return type.";

    /**
     * constructor for missing method return value, with line number
     *
     * @param line line of unsupported command
     */
    public MissingMethodReturnType(int line) {
        super(line, INFORMATIVE_ERROR_MSG);
    }

    /**
     * constructor for missing method return value, without line number
     */
    public MissingMethodReturnType() {
        super(INFORMATIVE_ERROR_MSG);
    }
}
