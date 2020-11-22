package oop.ex6.verifier;

/**
 * exception of missing opening brackets("{") after condition
 *
 * @author rina.karnauch, edenkeidar
 */
public class MissingOpeningBracketsAtConditionLine extends SyntaxError {

    private static final String INFORMATIVE_MSG = "missing '{' right after condition line";

    /**
     * constructor for unsupported command exception, without line number.
     */
    public MissingOpeningBracketsAtConditionLine() {
        super(INFORMATIVE_MSG);
    }
}
