package oop.ex6.verifier;

/**
 * parameters sent to method call won't match the methods wanted params.
 *
 * @author rina.karnauch edenkeider
 */
public class OutmatchingParametersToMethodCall extends UnsupportedCommand {

    private static final String INFORMATIVE_MSG = "none matching parameters given to method call.";

    /**
     * constructor for unsupported command exception, without line number.
     */
    public OutmatchingParametersToMethodCall() {
        super(INFORMATIVE_MSG);
    }
}
