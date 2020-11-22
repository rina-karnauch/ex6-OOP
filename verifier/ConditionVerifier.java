package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class of condition verifier which implement the verifier function interface and tests out condition.
 *
 * @author rina.karnauch, edenkeider
 */
public class ConditionVerifier implements Verifier {

    /*
    paramLineString
     */
    private String paramLine;

    /*
    block of condition to send to check
     */
    private ArrayList<String> block;

    /*
    iterator to iterate over block.
     */
    private Iterator<String> blockIterator;

    /*
    global variables to use inside the condition
     */
    private GlobalsWrapper globals;

    /*
    methods we can use inside the condition
     */
    private MethodsWrapper methodsToUse;

    /*
    depth of block of the condition, so we know which globals or locals to use.
     */
    private int blockDepth;

    /**
     * a constructor for our condition line verifier
     * @param blockIterator iterator to go over the block
     * @param paramLine the line of parans
     * @param conditionGlobals the condition params
     * @param blockDepth the depth of block
     * @param methods methods to use in block
     */
    public ConditionVerifier(Iterator<String> blockIterator, String paramLine,
                             GlobalsWrapper conditionGlobals, int blockDepth, MethodsWrapper methods) {
        // the variables we can use inside our condition line
        // the line of the condition -> if(boolean arg){
        this.paramLine = paramLine;
        this.blockIterator = blockIterator;
        this.methodsToUse = methods;
        this.globals = conditionGlobals;
        this.blockDepth = blockDepth;
        this.block = new ArrayList<String>();
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
    public void test() throws MethodNameTaken, MissingVariableName,
            MissingMethodParameters, MissingMethodReturnType, UndeclaredVariableType,
            IllegalReturnValue, VariableNameTaken, UnexpectedValueToVariable, MissingValueInVariable,
            SyntaxError, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod,
            UnexpectedValueToMethodSignature, UnsupportedCommand, MissingMethodName, UnsupportedCommandInMethod,
            ValueToFinalVariableException, MissingParenthesisException, InconvertibleTypeAssignment {
        checkConditionParameters();
        createBlock();
        BlockVerifier blockVerifier = new BlockVerifier(this.block,
                globals, methodsToUse, blockDepth); // deeper block
        blockVerifier.test();
    }

    /*
    method to check conditions parameters
     */
    private void checkConditionParameters() throws UnsupportedParameterForCondition {
        String[] params = this.paramLine.split(SEPARATOR);
        for (String parameter : params) {
            parameter = parameter.trim(); //  remove spaces.
            checkSingleParameter(parameter);
        }
    }

    /*
    method to check single parameter in the condition param line
     */
    private void checkSingleParameter(String parameter) throws UnsupportedParameterForCondition {
        Matcher possibleBoolean = BOOLEAN_REGEX.matcher(parameter);
        if (!possibleBoolean.matches()) {
            Variable contained = globals.get(parameter);
            if (!checkType(contained)) {
                throw new UnsupportedParameterForCondition();
            }
        }
    }

    /*
    method to check if type matches conditions
     */
    private boolean checkType(Variable var) {
        if (var == null) {
            return false;
        }
        if (!var.hasValue()) {
            return false;
        }
        return var.getType().equals(VariableVerifier.VerifiedTypes.DOUBLE) ||
                var.getType().equals(VariableVerifier.VerifiedTypes.INT)
                || var.getType().equals(VariableVerifier.VerifiedTypes.BOOLEAN);
    }

    /*
    create block after the condition checking
     */
    // we already check parenthesis in the parser, no need to check again
    // BUT NEED TO CHECK WHEN WE END THE BLOCK.
    private void createBlock() throws MissingParenthesisException {
        int closingCounter = 0;
        int openingCounter = 1;
        this.block = new ArrayList<String>();
        while (this.blockIterator.hasNext() && (openingCounter != closingCounter)) {
            String line = this.blockIterator.next();
            if (OPENING_BRACKET.equals(line)) {
                openingCounter++;
            } else if (CLOSING_BRACKET.equals(line)) {
                closingCounter++;
            }
            this.block.add(line);
        }
        // if we reached end of file but opening is equal not closing counter, then its a parenthesis problem
        if (openingCounter != closingCounter) {
            throw new MissingParenthesisException();
        }
    }

    /**
     * getter for the iterator after passing all the lines of the code block
     *
     * @return iterator on string.
     */
    public Iterator<String> getIterator() {
        return this.blockIterator;
    }

}
