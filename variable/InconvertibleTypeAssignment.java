package oop.ex6.variable;

/**
 * trying to convert a type into a forbidden type exception
 *
 * @author edenkeider, rina.karnauch
 */
public class InconvertibleTypeAssignment extends VariableException {

    /*
     informative message for an inconvertible assignment into a variable
      */
    private final static String INFORMATIVE_ERROR_MESSAGE = "value assignment doesn't match variable's type.";

    /**
     * constructor for unsupported command exception, without line printing.
     */
    public InconvertibleTypeAssignment() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
