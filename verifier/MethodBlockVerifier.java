package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * method block verifier- verifies the method block- the block and the return and parameters.
 *
 * @author rina.karnauch, edenkeider
 */
public class MethodBlockVerifier implements Verifier {

    /*
    the lines of the method block
     */
    private ArrayList<String> methodBlock;

    /*
    method block after analyzing the signature of the method
     */
    private Method methodObject;

    /*
    external methods we are allowed to use in our block, to call those.
     */
    private MethodsWrapper otherMethods;

    /*
    global variables for current method
     */
    private GlobalsWrapper globalVariables;

    /*
    local variables wrapper
     */
    private LocalsWrapper localVariables;

    /*
    default depth of scope inside checking a method.
     */
    private static final int INSIDE_METHOD_DEFAULT_DEPTH = 1;

    /**
     * constructor to create the method block verifier
     *
     * @param methodBlock the block lines
     * @param declaration the method object
     * @param methods     methods to use
     */
    public MethodBlockVerifier(ArrayList<String> methodBlock, Method declaration,
                               MethodsWrapper methods) {
        this.methodBlock = methodBlock;
        this.methodObject = declaration;
        this.otherMethods = methods;
        this.globalVariables = otherMethods.getGlobals();
        this.localVariables = new LocalsWrapper();


        if (this.methodObject.getMethodParameters() != null) { // if we got parameters.
            this.localVariables.addAll(this.methodObject.getMethodParameters());
            // parameters are local variables
        }
    }

    /**
     * method of the functional interface to test section of the code, each method which implements the verifier
     * will test out by a different strategy according to the code section we check.
     *
     * @throws MissingMethodReturnType              method return type wasn't declared
     * @throws IllegalReturnValue                   illegal return value to method
     * @throws MissingMethodName                    missing method name
     * @throws MissingMethodParameters              missing params to method
     * @throws UnexpectedValueToMethodSignature     extra value to method signature
     * @throws MethodNameTaken                      method name is already taken
     * @throws UnexpectedParameterInMethodSignature given parameter to method call is not wanted
     * @throws MissingVariableName                  variable name not given
     * @throws UndeclaredVariableType               variable type not given
     * @throws UnsupportedCommand                   unknown command in sjava file
     * @throws InconvertibleTypeAssignment          type given does not match assignment
     * @throws ValueToFinalVariableException        value trying to assign to a final var
     * @throws VariableNameTaken                    variable name is taken already
     * @throws UnexpectedValueToVariable            no matching type given to variable value
     * @throws MissingValueInVariable               missing value in assign to variable
     * @throws ReturnMissingAtEndOfMethod           no return in end of method
     * @throws UnsupportedCommandInMethod           command format unsupported
     * @throws MissingParenthesisException          missing parent' in block
     * @throws SyntaxError                          syntax error in some line
     */
    @Override
    public void test() throws ReturnMissingAtEndOfMethod, UnsupportedCommandInMethod, SyntaxError,
            UndeclaredVariableType, UnsupportedCommand, VariableNameTaken, ValueToFinalVariableException,
            UnexpectedValueToVariable, MissingVariableName, MissingValueInVariable,
            UnexpectedValueToMethodSignature, IllegalReturnValue, MissingMethodName, MethodNameTaken,
            UnexpectedParameterInMethodSignature, MissingMethodParameters, MissingMethodReturnType,
            MissingParenthesisException, InconvertibleTypeAssignment {

        checkReturnFromMethod(); // we removed the return value if it's correct

        GlobalsWrapper variablesForUsage = new GlobalsWrapper();
        variablesForUsage.addAll(this.globalVariables.getGlobals());
        variablesForUsage.addAll(this.localVariables.getLocals());

        BlockVerifier verifier = new BlockVerifier(methodBlock, variablesForUsage, otherMethods,
                INSIDE_METHOD_DEFAULT_DEPTH);
        verifier.test();
    }

    /*
    method to check return value of method
     */
    private void checkReturnFromMethod() throws ReturnMissingAtEndOfMethod {
        // line is trimmed from end and front.
        int methodLength = methodBlock.size() - 1;
        String returnLine = methodBlock.get(methodLength - 1);
        returnLine = returnLine.trim();
        Matcher returnAtBlockEndMatcher = RETURN_REGEX.matcher(returnLine); // exists return; at end.
        if (!returnAtBlockEndMatcher.matches()) {
            throw new ReturnMissingAtEndOfMethod();
        }
        methodBlock.remove(methodLength - 1); // no need to check again.
        methodBlock.remove(0);
    }

}
