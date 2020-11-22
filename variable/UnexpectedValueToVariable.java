package oop.ex6.variable;

/**
 * unexpected value given to variable declaration.
 *
 * @author edenkeider, rina.karnauch
 */
public class UnexpectedValueToVariable extends VariableException {

    /*
   informative message for a missing name of variable
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "unexpected string in value declaration.";

    /**
     * constructor for unsupported command exception
     *
     * @param line line of unsupported command
     */
    public UnexpectedValueToVariable(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }


    /**
     * constructor for unsupported command exception, without line printing.
     */
    public UnexpectedValueToVariable() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }

}
