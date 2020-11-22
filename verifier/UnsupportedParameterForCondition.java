package oop.ex6.verifier;

/**
 * unsupported parameter given to the condition param line
 *
 * @author edenkeider, rina.karnauch
 */
public class UnsupportedParameterForCondition extends UnsupportedCommand {

    private static final String INFORMATIVE_MSG = "Unsupported parameter was given to condition line.";

    /**
     * constructor for unsupported command exception, with line number.
     */
    public UnsupportedParameterForCondition() {
        super(INFORMATIVE_MSG);
    }
}
