package oop.ex6.verifier;

/**
 * nonexistent method called  in code file
 *
 * @author edenkeider, rina.karnauch
 */
public class UnknownMethodCalled extends UnsupportedCommand {

    /*
    informative msg for the exception
     */
    private static final String INFORMATIVE_MSG = "Unknown method name was called.";

    /**
     * constructor for unsupported command exception, with line number.
     */
    public UnknownMethodCalled() {
        super(INFORMATIVE_MSG);
    }
}
