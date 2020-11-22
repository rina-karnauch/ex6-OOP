package oop.ex6.verifier;

/**
 * exception of assignment to undeclared variable
 *
 * @author rina.karnauch , edenkeider
 */
public class AssignmentToUndeclaredVariable extends UnsupportedCommand {

    private static final String INFORMATIVE_MSG = "assignment to nonexistent variable or a variable in an inner depth";


    /**
     * constructor for unsupported command exception, without line number.
     */
    public AssignmentToUndeclaredVariable() {
        super(INFORMATIVE_MSG);
    }
}
