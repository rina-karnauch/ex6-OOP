package oop.ex6.variable;

/**
 * variable name is already taken exception
 *
 * @author edenkeidar, rina.karnauch
 */
public class VariableNameTaken extends VariableException {

    /*
  informative message for a missing name of variable
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "variable name is already taken.";

    /**
     * constructor for unsupported command exception
     *
     * @param line line of unsupported command
     */
    public VariableNameTaken(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * constructor for unsupported command exception
     */
    public VariableNameTaken() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
