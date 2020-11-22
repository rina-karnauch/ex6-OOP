package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.parsing.Parser;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author rina.karnauch edenkeider
 */
public class VerificationStrategy {

    /*
    verified global variables
    */
    private static GlobalsWrapper globalVariables = new GlobalsWrapper();

    /*
    verified methods
     */
    private static MethodsWrapper methodsWrapper = new MethodsWrapper();

    /*
    constructor is private to strategy is a singleton.
     */
    private VerificationStrategy() {
        // so we don't create instances
    }

    /**
     * @param parser parser object, of parsed sjava file into information we need to verify.
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
    public static void getVerifications(Parser parser) throws MissingMethodName,
            UnexpectedValueToMethodSignature, IllegalReturnValue, MissingMethodParameters,
            MissingMethodReturnType, UndeclaredVariableType, SyntaxError, ValueToFinalVariableException,
            VariableNameTaken, UnsupportedCommand, MissingValueInVariable, MissingVariableName,
            UnexpectedValueToVariable, MethodNameTaken, UnexpectedParameterInMethodSignature,
            UnsupportedCommandInMethod, ReturnMissingAtEndOfMethod, MissingParenthesisException,
            InconvertibleTypeAssignment {
        clearStructures(); // clear previous values.
        globalVariableStrategy(parser);
        methodsStrategy(parser);
    }

    /*
    method to clear previous data structures from past data
     */
    private static void clearStructures() {
        VerificationStrategy.globalVariables.clear();
        VerificationStrategy.methodsWrapper.clear();
    }


    /*
    helper method to verify the global variables by a global variable strategy
     */
    private static void globalVariableStrategy(Parser parser) throws ValueToFinalVariableException,
            SyntaxError, UnsupportedCommand, UnexpectedValueToVariable, UndeclaredVariableType, VariableNameTaken,
            MissingValueInVariable, MissingVariableName, InconvertibleTypeAssignment, UnexpectedValueToMethodSignature,
            MissingParenthesisException, IllegalReturnValue, MissingMethodName, MethodNameTaken,
            UnsupportedCommandInMethod, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod,
            MissingMethodParameters, MissingMethodReturnType {
        ArrayList<String> toBeVariables = parser.getGlobalVariables();
        ArrayList<Integer> variableLine = parser.getVariableLines();
        GlobalVariableVerifier verifier;
        verifier = new GlobalVariableVerifier(toBeVariables, variableLine);
        verifier.test();
        globalVariables = verifier.getGlobalsWrapper();
    }

    /*
    helper method to verify the method blocks and declarations by a methods verifier strategy
     */
    private static void methodsStrategy(Parser parser) throws MissingMethodName,
            MissingMethodParameters, MissingMethodReturnType, UnexpectedValueToMethodSignature, IllegalReturnValue,
            MethodNameTaken, UnexpectedParameterInMethodSignature, MissingVariableName, UndeclaredVariableType,
            UnsupportedCommand, SyntaxError, VariableNameTaken, ValueToFinalVariableException,
            MissingValueInVariable, ReturnMissingAtEndOfMethod, UnsupportedCommandInMethod,
            UnexpectedValueToVariable, MissingParenthesisException, InconvertibleTypeAssignment {
        // checking method declarations
        methodDeclarationsStrategy(parser);
        // checking the methods themselves.
        methodBlocksStrategy();
    }

    /*
    helper method to verify method declarations by a method declaration verifier strategy
     */
    private static void methodDeclarationsStrategy(Parser parser)
            throws MissingMethodName,
            UnexpectedValueToMethodSignature, IllegalReturnValue, MissingMethodParameters, MissingMethodReturnType,
            MethodNameTaken, UnexpectedParameterInMethodSignature, UndeclaredVariableType, unsupportedType,
            MissingVariableName, InconvertibleTypeAssignment, VariableNameTaken {

        MethodSignatureVerifier verifier;
        ArrayList<String> declarations = parser.getMethodDeclarations();
        ArrayList<Integer> declarationLines = parser.getMethodSignatureLine();
        ArrayList<ArrayList<String>> blocks = parser.getMethodBlocks();

        VerificationStrategy.methodsWrapper = new MethodsWrapper(globalVariables);

        int i = 0; // counter for the index of method line we currently get.
        for (String methodSignature : declarations) {

            int line = declarationLines.get(i); // line of signature in sjava code file.
            // current existing methods:
            HashSet<String> currentMethods = VerificationStrategy.methodsWrapper.getMethodNames();

            verifier = new MethodSignatureVerifier(methodSignature, currentMethods, line); // the verifier.
            verifier.test();
            Method verifiedMethod = verifier.getVerified();

            // method wrapper object, adding the method to the wrapper.
            // blocks.get(i) is the block of the fitting signature.
            VerificationStrategy.methodsWrapper.add(verifiedMethod, verifiedMethod.getName(), line, blocks.get(i));

            i++;
        }
    }

    /*
    helper method to verify the method blocks
     */
    private static void methodBlocksStrategy() throws MethodNameTaken,
            MissingVariableName, MissingMethodParameters, UnsupportedCommandInMethod,
            ValueToFinalVariableException, IllegalReturnValue, VariableNameTaken,
            UnexpectedValueToVariable, MissingValueInVariable, SyntaxError, UnexpectedParameterInMethodSignature,
            ReturnMissingAtEndOfMethod, UnexpectedValueToMethodSignature, UnsupportedCommand, MissingMethodReturnType,
            UndeclaredVariableType, MissingMethodName, MissingParenthesisException, InconvertibleTypeAssignment {
        MethodsWrapper wrapper = VerificationStrategy.methodsWrapper;
        for (int i = 0; i < wrapper.size(); i++) {
            ArrayList<String> currentMethodBlock = wrapper.getBlockByIndex(i);
            Method currentMethod = wrapper.getMethodByIndex(i);
            handleMethodBlock(currentMethodBlock, currentMethod);
        }
    }

    /*
    helper method to handle one single method block one by one
    with the method declaration which was constructed before
     */
    private static void handleMethodBlock(ArrayList<String> singleBlock, Method currentMethod) throws MethodNameTaken,
            IllegalReturnValue, MissingMethodParameters, UnsupportedCommandInMethod, UnexpectedValueToMethodSignature,
            VariableNameTaken, UnexpectedValueToVariable, MissingValueInVariable, SyntaxError, MissingMethodName,
            UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod, MissingVariableName, UnsupportedCommand,
            MissingMethodReturnType, UndeclaredVariableType, ValueToFinalVariableException,
            MissingParenthesisException, InconvertibleTypeAssignment {
        MethodBlockVerifier verifier;
        verifier = new MethodBlockVerifier(singleBlock, currentMethod,
                VerificationStrategy.methodsWrapper);
        verifier.test();
    }

}
