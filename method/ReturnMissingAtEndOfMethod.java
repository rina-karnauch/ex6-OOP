package oop.ex6.method;

/**
 * missing return line at end of method block
 *
 * @author rina.karnauch , edenkeider
 */
public class ReturnMissingAtEndOfMethod extends MethodException {

    private static final String INFORMATIVE_MSG = "missing return statement at end of method.";

    /**
     * constructor for method exception, missing return at end of method block, with line msg.
     *
     * @param line line of unsupported command
     */
    public ReturnMissingAtEndOfMethod(int line) {
        super(line, INFORMATIVE_MSG);
    }

    /**
     * constructor for method exception, missing return at end of method block.
     */
    public ReturnMissingAtEndOfMethod() {
        super(INFORMATIVE_MSG);
    }
}
