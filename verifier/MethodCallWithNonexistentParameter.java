package oop.ex6.verifier;

/**
 * class of exception of method call with none existent parameter given to it
 *
 * @author rina.karnauch edenkeidar
 */
public class MethodCallWithNonexistentParameter extends UnsupportedCommand {

    /*
    informative msg given to exception
     */
    private static final String INFORMATIVE_MSG = "none existent parameter given to method call.";

    /**
     * constructor for unsupported command exception, without line number.
     */
    public MethodCallWithNonexistentParameter() {
        super(INFORMATIVE_MSG);
    }
}
