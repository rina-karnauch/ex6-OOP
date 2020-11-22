package oop.ex6.verifier;

/**
 * abstract class of unsupported commands in sjava conformer code
 *
 * @author rina.karnauch, edenkeidar
 */
public abstract class UnsupportedCommand extends Exception {

    /*
     * abstract added error message before informative line about the exception, WITH LINE NUMBER
     */
    private final static String UNSUPPORTED_COMMAND_ERROR_MESSAGE_WITH_LINE = "Unsupported command in line: ";

    /*
     * abstract added error message before informative line about the exception
     */
    private final static String UNSUPPORTED_COMMAND_ERROR_MESSAGE = "Unsupported command:";

    /**
     * constructor for unsupported command exception, with line number.
     *
     * @param line        line of unsupported command
     * @param informative informative message about the exception
     */
    public UnsupportedCommand(int line, String informative) {
        super(UNSUPPORTED_COMMAND_ERROR_MESSAGE_WITH_LINE + line + "," + informative);
    }

    /**
     * constructor for unsupported command exception, without line number.
     *
     * @param informative informative message about the exception
     */
    public UnsupportedCommand(String informative) {
        super(UNSUPPORTED_COMMAND_ERROR_MESSAGE_WITH_LINE + informative);
    }
}
