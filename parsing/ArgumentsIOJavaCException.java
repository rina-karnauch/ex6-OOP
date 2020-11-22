package oop.ex6.parsing;

/**
 * class of IOException, indicates about an error with the arguments given to the sjava conformer
 *
 * @author rina.karnauch, edenkeidar
 */
public class ArgumentsIOJavaCException extends IOJavaCExceptions {

    /*
    informative message for the current exception
     */
    private static final String INFORMATIVE_EXCEPTION_MESSAGE = "command arguments error";

    /**
     * constructor for an illegal command arguments in  IO exception.
     */
    public ArgumentsIOJavaCException() {
        super(INFORMATIVE_EXCEPTION_MESSAGE);
    }

}
