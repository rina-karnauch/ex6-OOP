package oop.ex6.variable;

import oop.ex6.verifier.UnsupportedCommand;

/**
 * class for an unsupported exception in sjava language conformer.
 * thrown when a none existent type is called.
 *
 * @author edenkeidar, rina.karnauch
 */
public class unsupportedType extends UnsupportedCommand {

    /*
    informative message for current exception
     */
    private final static String INFORMATIVE_ERROR_MESSAGE = "unknown type for sjava format.";

    /**
     * constructor for unsupported command exception
     *
     * @param line line of unsupported command
     */
    public unsupportedType(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * constructor for unsupported command exception
     */
    public unsupportedType() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
