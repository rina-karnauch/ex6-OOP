package oop.ex6.parsing;

/**
 * class of IOException, indicates about an error with the file given to the sjava conformer, it is missing.
 *
 * @author rina.karnauch, edenkeidar
 */
public class MissingFileException extends IOJavaCExceptions {

    /*
    informative message for the current exception for a missing file exception.
     */
    private static final String INFORMATIVE_EXCEPTION_MESSAGE = "missing file.";

    /**
     * constructor for a missing file IO exception.
     */
    public MissingFileException() {
        super(INFORMATIVE_EXCEPTION_MESSAGE);
    }
}
