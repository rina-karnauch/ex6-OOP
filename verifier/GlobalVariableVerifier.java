package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.ArrayList;

/**
 * clas of global variable verifier, extends variable verifier.
 *
 * @author rina.karnauch, edenkeider
 */
public class GlobalVariableVerifier extends VariableVerifier implements Verifier {

    /*
    strings of global variables code lines we need to check
     */
    private ArrayList<String> globalStrings;

    /*
   lines of global declarations
    */
    private ArrayList<Integer> globalLines;

    /*
    depth of global variables
     */
    private static final int GLOBAL_DEPTH = 0;

    /**
     * constructor for our globals variables verifier
     *
     * @param globalStrings our array list of global strings
     * @param globalLines global lines of variables reading
     */
    public GlobalVariableVerifier(ArrayList<String> globalStrings, ArrayList<Integer> globalLines) {
        this.globalStrings = globalStrings;
        this.globalLines = globalLines;
    }

    /**
     * method of the functional interface to test section of the code, each method which implements the verifier
     * will test out by a different strategy according to the code section we check.
     *
     * @throws MissingVariableName           variable name not given
     * @throws UndeclaredVariableType        variable type not given
     * @throws InconvertibleTypeAssignment   type given does not match assignment
     * @throws ValueToFinalVariableException value trying to assign to a final var
     * @throws VariableNameTaken             variable name is taken already
     * @throws UnexpectedValueToVariable     no matching type given to variable value
     * @throws MissingValueInVariable        missing value in assign to variable
     */
    @Override
    public void test() throws SyntaxError, UnsupportedCommand, VariableNameTaken,
            ValueToFinalVariableException, UnexpectedValueToVariable, MissingValueInVariable,
            MissingVariableName, UndeclaredVariableType, InconvertibleTypeAssignment, UnexpectedValueToMethodSignature,
            MissingParenthesisException, IllegalReturnValue, MissingMethodName, MethodNameTaken,
            UnsupportedCommandInMethod, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod,
            MissingMethodParameters, MissingMethodReturnType {
        BlockVerifier globalBlock = new BlockVerifier(this.globalStrings, this.globals, new MethodsWrapper(),
                GLOBAL_DEPTH);
        globalBlock.test();
        this.globals.addAll(globalBlock.getBlockLocals());
    }

    /**
     * a method to get the globals created from the verifier in their wrapper
     *
     * @return globals wrapped up class
     */
    GlobalsWrapper getGlobalsWrapper() {
        return this.globals;
    }

    /**
     * check if variable name isn't already taken.
     *
     * @param name  name we would like to create
     * @param depth the depth of the variable.
     * @throws VariableNameTaken variable name is already taken exception.
     */
    protected void variableNameCheck(String name, int depth) throws VariableNameTaken {
        if (globals.contains(name)) {
            throw new VariableNameTaken();
        }
    }
}
