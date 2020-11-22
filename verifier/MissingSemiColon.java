package oop.ex6.verifier;

/**
 * missing semi colon (";") at end of a line
 *
 * @author rina.karnauch  edenekeider
 */
public class MissingSemiColon extends SyntaxError {

    /*
   informative message for a missing comma at end of line
    */
    private final static String INFORMATIVE_ERROR_MESSAGE = "missing ';' at end of line.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public MissingSemiColon(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public MissingSemiColon() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
