package oop.ex6.verifier;


/**
 * a type sent to param isn't correct exception
 *
 * @author rina.karnauch edenkeider
 */
public class OutmatchingParameterType extends UnsupportedCommand {

    private static final String INFORMATIVE_MSG = "parameter sent to the call doesn't match the needed type.";

    /**
     * constructor for unsupported command exception, without line number.
     */
    public OutmatchingParameterType() {
        super(INFORMATIVE_MSG);
    }
}
