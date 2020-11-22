package oop.ex6.variable;

/**
 * class of missing value given to value we try to assign to
 *
 * @author rina.karnauch, edenkeider
 */
public class MissingValueInVariable extends VariableException {

    /*
   informative message for a missing name of variable
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "value was meant to be initialized by '=' string but " +
            "didn't receive a value";

    /**
     * constructor for unsupported command exception
     *
     * @param line line of unsupported command
     */
    public MissingValueInVariable(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * constructor for unsupported command exception without line number.
     */
    public MissingValueInVariable() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
