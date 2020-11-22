package oop.ex6.variable;

/**
 * class of an exception, of trying to put a value inside a final variable.
 *
 * @author edenkeider, rina.karnauch
 */
public class ValueToFinalVariableException extends VariableException {

    /*
   informative message for current exception
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "forbidden initialization of final variable.";

    /**
     * constructor for final initialization command
     *
     * @param line line of unsupported command
     */
    public ValueToFinalVariableException(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * constructor for final initialization command
     */
    public ValueToFinalVariableException() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }

}
