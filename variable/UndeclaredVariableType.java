package oop.ex6.variable;

/**
 * type not given to a variable exception
 *
 * @author edenkeider, rina.karnauch
 */
public class UndeclaredVariableType extends VariableException {

    /*
   informative message for a missing type declaration.
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "missing type of variable.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public UndeclaredVariableType(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public UndeclaredVariableType() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
