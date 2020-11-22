package oop.ex6.parsing;

/**
 * abstract class of IO Exceptions with sjava conformer code
 *
 * @author rina.karnauch, edenkeidar
 */
public abstract class IOJavaCExceptions extends Exception {

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String IO_ERROR_MESSAGE = "IO Exception: ";

    /**
     * constructor for such an exception
     *
     * @param informative informative message about the exception
     */
    public IOJavaCExceptions(String informative) {
        super(IO_ERROR_MESSAGE + informative);
    }
}
