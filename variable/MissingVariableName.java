package oop.ex6.variable;

/**
 * name not given to a variable exception
 *
 * @author edenkeider, rina.karnauch
 */
public class MissingVariableName extends VariableException {

    /*
   informative message for a missing name of variable
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "missing name of variable.";

    /**
     * constructor for unsupported command exception
     *
     * @param line        line of unsupported command
     */
    public MissingVariableName(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE );
    }

    /**
     * constructor for unsupported command exception
     */
    public MissingVariableName() {
        super(INFORMATIVE_ERROR_MESSAGE );
    }
}
