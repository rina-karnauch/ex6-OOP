package oop.ex6.parsing;

/**
 * already closed file exception
 *
 * @author rina.karnauch, edenkeider
 */
public class FileClosedException extends IOJavaCExceptions {

    /*
    informative message for the current exception of an already closed file exception.
     */
    private static final String INFORMATIVE_EXCEPTION_MESSAGE = "file already closed.";

    /**
     * constructor for such an exception
     */
    public FileClosedException() {
        super(INFORMATIVE_EXCEPTION_MESSAGE);
    }
}
