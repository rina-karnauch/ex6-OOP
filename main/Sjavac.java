package oop.ex6.main;


import oop.ex6.parsing.ArgumentsIOJavaCException;
import oop.ex6.method.*;
import oop.ex6.parsing.*;
import oop.ex6.variable.*;
import oop.ex6.verifier.SyntaxError;
import oop.ex6.verifier.UnsupportedCommand;
import oop.ex6.verifier.VerificationStrategy;

/**
 * class of Sjavac system to check simple sjava code
 *
 * @author rina.karnauch, edenkeidar
 */
public class Sjavac {

    /*
     * return value from main sjavac class, upon legal code file
     */
    private static final int RETURN_UPON_LEGAL_CODE = 0;

    /*
     * return value from main sjavac class, upon illegal code file
     */
    private static final int RETURN_UPON_ILLEGAL_CODE = 1;

    /*
     * return value from main sjavac class, upon IO errors
     */
    private static final int RETURN_UPON_IO_ERRORS = 2;

    /*
    amount of correct arguments given to us
    */
    private static final int AMOUNT_OF_ARGS = 1;

    /*
    file name index in arguments array from main method
     */
    private static final int FILE_NAME_INDEX = 0;


    /**
     * main method to confirm the validity of the sjava file.
     *
     * @param args the arguments from the command line
     */
    public static void main(String[] args) {
        try {
            String fileName = checkArgumentsLine(args);
            Parser parser = new Parser(fileName);
            // now we have a parsed object.
            // now we need to verify each part of it.
            VerificationStrategy.getVerifications(parser);
            System.out.println(RETURN_UPON_LEGAL_CODE);
        } catch (IOJavaCExceptions fileError) {
            fileExceptionPrinter(fileError);
        } catch (MissingParenthesisException | IllegalParenthesisFormatException | UnknownLineFormat |
                MissingMethodName | UnexpectedValueToMethodSignature | IllegalReturnValue |
                MissingMethodParameters | MissingMethodReturnType | UndeclaredVariableType |
                SyntaxError | ValueToFinalVariableException | VariableNameTaken | UnsupportedCommand |
                MissingValueInVariable | MissingVariableName | UnexpectedValueToVariable | MethodNameTaken |
                UnexpectedParameterInMethodSignature | UnsupportedCommandInMethod | ReturnMissingAtEndOfMethod |
                InconvertibleTypeAssignment error) {
            illegalCodePrinter(error);
        }
    }


    /*
    method to check are given arguments from the command line
     */
    private static String checkArgumentsLine(String[] args) throws ArgumentsIOJavaCException {
        if (args.length != AMOUNT_OF_ARGS) {
            throw new ArgumentsIOJavaCException();
        }
        return args[FILE_NAME_INDEX];
    }

    /*
    language conformer result printing helper method, upon file exception
     */
    private static void fileExceptionPrinter(Exception error) {
        System.out.println(RETURN_UPON_IO_ERRORS);
        System.err.println(error.getMessage());
    }

    /*
    language conformer result printing helper method, upon illegal code
     */
    private static void illegalCodePrinter(Exception error) {
        System.out.println(RETURN_UPON_ILLEGAL_CODE);
        System.err.println(error.getMessage());
    }

}
