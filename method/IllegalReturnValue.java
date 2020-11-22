package oop.ex6.method;

/**
 * illegal return value to in a method signature.
 *
 * @author rina.karnauch, edenkeidar
 */
public class IllegalReturnValue extends MethodException {

    /*
  informative message for current exception
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "forbidden return value for a method.";

    /**
     * constructor for illegal return value of a method
     *
     * @param line line of unsupported command
     */
    public IllegalReturnValue(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * constructor for illegal return value of a method
     */
    public IllegalReturnValue() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
