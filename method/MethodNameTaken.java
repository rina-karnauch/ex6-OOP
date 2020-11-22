package oop.ex6.method;

/**
 * illegal method name repetition in method signature, extends method exceptions.
 *
 * @author rina.karnauch, edenkeidar
 */
public class MethodNameTaken extends MethodException {
    /*
  informative message for a taken method name.
   */
    private final static String INFORMATIVE_ERROR_MESSAGE = "method's name already taken.";

    /**
     * a syntax error constructor of exception
     *
     * @param line number of line of exception
     */
    public MethodNameTaken(int line) {
        super(line, INFORMATIVE_ERROR_MESSAGE);
    }

    /**
     * a syntax error constructor of exception
     */
    public MethodNameTaken() {
        super(INFORMATIVE_ERROR_MESSAGE);
    }
}
