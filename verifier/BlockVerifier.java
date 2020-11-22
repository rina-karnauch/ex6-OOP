package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

/**
 * verifier representing a block verifier- verifies a block of code between "{" and "}".
 *
 * @author rina.karnauch , edenkeider.
 */
public class BlockVerifier implements Verifier {

    /*
    wrapper of globals to that code
     */
    private GlobalsWrapper variablesToUse = new GlobalsWrapper();

    /*
    locals of inner block
     */
    private ArrayList<Variable> blockLocals = new ArrayList<Variable>();

    /*
    methods wrapper to use inside the block
     */
    private MethodsWrapper methodsToUse = new MethodsWrapper();

    /*
    block of code we check
     */
    private ArrayList<String> block;

    /*
    the depth of the block
     */
    private int blockDepth;


    /**
     * constructor for the block verifier object.
     *
     * @param block          lines of block.s
     * @param variablesToUse variables we are able to use inside block.
     * @param methodsToUse   methods we are able to use inside block.
     * @param depth          depth of block to know which variables in which depths we can use.
     */
    public BlockVerifier(ArrayList<String> block, GlobalsWrapper variablesToUse,
                         MethodsWrapper methodsToUse, int depth) {
        this.variablesToUse = variablesToUse;
        this.methodsToUse = methodsToUse;
        this.block = block;
        this.blockDepth = depth;
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
    public void test() throws ValueToFinalVariableException,
            IllegalReturnValue, MethodNameTaken, MissingMethodName, MissingMethodReturnType,
            MissingMethodParameters, UnexpectedValueToMethodSignature, VariableNameTaken,
            UnexpectedValueToVariable, MissingValueInVariable, MissingVariableName,
            UndeclaredVariableType, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod,
            UnsupportedCommandInMethod, MissingParenthesisException, InconvertibleTypeAssignment, SyntaxError,
            UnsupportedCommand {

        Iterator<String> blockIterator = block.iterator();
        while (blockIterator.hasNext()) {
            String line = blockIterator.next(); // the String of the line.
            line = line.trim(); // delete spaces from beginning and ending.

            // matchers
            Matcher localVariableDecMatcher = LOCAL_VARIABLE_DECLARATION_REGEX.matcher(line);
            Matcher conditionBeginMatcher = CONDITION_REGEX.matcher(line);
            Matcher methodCallMatcher = METHOD_CALL_REGEX.matcher(line);
            Matcher newVariableAssignmentMatcher = LOCAL_VARIABLE_ASSIGNMENT_REGEX.matcher(line);
            Matcher existingVariableAssignmentMatcher = EXISTING_VARIABLE_ASSIGNMENT_REGEX.matcher(line);
            Matcher commentMatcher = COMMENT_REGEX.matcher(line);
            Matcher returnLine = RETURN_REGEX.matcher(line);
            Matcher closingScope = CLOSING_REGEX.matcher(line);

            // line is trimmed from end and front.
            // we check existence of ; after the identification of the line
            // check of ";"- but "{" check is outside in condition determining.

            if (localVariableDecMatcher.lookingAt() || newVariableAssignmentMatcher.lookingAt()) {
                verifyLocalVariable(line);
            } else if (conditionBeginMatcher.matches()) {
                // "{" at the end of the line is already checked in end matcher
                // need to update the iterator so we go on
                // the capture group is the params themselves inside.
                String conditionParam = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
                blockIterator = verifyCondition(blockIterator, conditionParam);
            } else if (methodCallMatcher.lookingAt()) {
                verifyMethodCall(line);
            } else if (existingVariableAssignmentMatcher.lookingAt()) {
                verifyAssignment(line);
            } else if (!commentMatcher.lookingAt() && !returnLine.matches() && !closingScope.matches()) {
                throw new UnsupportedCommandInMethod();
            }
            blockIterator.remove(); // remove the line, it was cleared to be right.
        }
    }

    /*
    method helper for code block to send line to local variable verifier
     */
    private void verifyLocalVariable(String line) throws MissingSemiColon, UndeclaredVariableType,
            unsupportedType, VariableNameTaken, ValueToFinalVariableException, MissingValueInVariable,
            MissingVariableName, UnexpectedValueToVariable, InconvertibleTypeAssignment {
        // line is trimmed from end and front.
        line = removeSemiColon(line);
        LocalVariableVerifier verifier = new LocalVariableVerifier(line, blockDepth, this.variablesToUse,
                this.blockLocals);
        verifier.test();
        ArrayList<Variable> verifiedLocals = verifier.getVerified();
        // adding locals to current method block, to know that they exists
        blockLocals.addAll(verifiedLocals);
    }

    /*
   method helper for code block to send line to condition verifier
    */
    private Iterator<String> verifyCondition(Iterator<String> iter, String conditionParam) throws
            MethodNameTaken, MissingVariableName, MissingMethodParameters, MissingMethodReturnType,
            UndeclaredVariableType, IllegalReturnValue, VariableNameTaken, UnexpectedValueToVariable,
            MissingValueInVariable, SyntaxError, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod,
            UnexpectedValueToMethodSignature, UnsupportedCommand, MissingMethodName, UnsupportedCommandInMethod,
            ValueToFinalVariableException, MissingParenthesisException, InconvertibleTypeAssignment {
        // line is trimmed from end and front.
        // those are global for the condition block- they know them as globals.
        ArrayList<Variable> globalsForCondition = new ArrayList<Variable>();
        globalsForCondition.addAll(this.variablesToUse.getGlobals());
        globalsForCondition.addAll(blockLocals);

        GlobalsWrapper globalsForConditionBlock = new GlobalsWrapper();
        globalsForConditionBlock.addAll(globalsForCondition);

        // check the condition itself.
        // condition Param is already the captured group of params between "(" and "){"
        ConditionVerifier verifier = new ConditionVerifier(iter, conditionParam, globalsForConditionBlock,
                blockDepth + 1, methodsToUse);
        verifier.test();
        return verifier.getIterator();
    }

    /*
    method helper for code block to send line to local method call verifier
     */
    private void verifyMethodCall(String line) throws UnsupportedCommandInMethod, UnknownMethodCalled,
            MethodCallWithNonexistentParameter, OutmatchingParametersToMethodCall,
            OutmatchingParameterType, MissingSemiColon, InconvertibleTypeAssignment {
        // line is trimmed from end and front.
        // removing the semi colon
        line = removeSemiColon(line);

        Matcher methodCallMatcher = METHOD_CALL_REGEX.matcher(line);
        String methodName;
        if (methodCallMatcher.find()) {
            methodName = methodCallMatcher.group(1);
            line = Verifier.cutAndTrimToEnd(methodCallMatcher, 0, line);
            // line has unwanted values left to it.
            if (!line.isEmpty()) {
                throw new OutmatchingParametersToMethodCall();
            }
        } else {
            throw new UnsupportedCommandInMethod();
        }
        Method method = methodsToUse.getMethod(methodName); // can accept null or empty or none existent
        // will return null
        if (method == null) {
            throw new UnknownMethodCalled();
        }

        ArrayList<Variable> variablesForCall = variablesToUse.getGlobals();
        variablesForCall.addAll(blockLocals);
        String params = methodCallMatcher.group(2);
        MethodCallVerifier verifier = new MethodCallVerifier(method, params, variablesForCall);
        verifier.test();
    }

    /*
   method helper for code block to send line to assignment to variable verifier
    */
    private void verifyAssignment(String line) throws MissingSemiColon, AssignmentToUndeclaredVariable,
            ValueToFinalVariableException, InconvertibleTypeAssignment {
        line = removeSemiColon(line);

        String[] assignedParams = line.split(COMMA, -1);

        for (String param : assignedParams) {
            String variableName = param.substring(0, param.indexOf(EQUAL_SIGN)).trim();
            String assignment = param.substring(param.indexOf(EQUAL_SIGN) + 1).trim();

            Variable thisVariable = checkVariableExistenceInBlock(variableName);
            Variable assignedVar = checkVariableAssigned(assignment);

            // the type of our variable.
            VariableVerifier.VerifiedTypes variableType = thisVariable.getType();

            // the type we are inserting.
            VariableVerifier.VerifiedTypes assignmentType;

            if (assignedVar == null) {
                // assignment into a final variable is checked afterwards.
                // we check the string we insert
                assignmentType = VariableVerifier.VerifiedTypes.getTypeOfString(assignment);
            } else {
                // the variable we insert, we get its type
                assignmentType = assignedVar.getType();
            }

            variableType.checkConversation(assignmentType);
            thisVariable.setValue(true);
        }
    }

    /*
    check variable assigned existence
     */
    private Variable checkVariableAssigned(String assigned) {
        for (Variable local : this.variablesToUse.getGlobals()) {
            if (assigned.equals(local.getName())) {
                return local;
            }
        }
        return null;
    }

    /*
   method helper to check if variable exists and able to be used in current block
    */
    private Variable checkVariableExistenceInBlock(String variableName) throws AssignmentToUndeclaredVariable {
        Variable possibleVar = this.variablesToUse.get(variableName);
        if (possibleVar != null) {
            return possibleVar;
        }
        for (Variable local : this.blockLocals) {
            if (variableName.equals(local.getName())) {
                return local;
            }
        }
        throw new AssignmentToUndeclaredVariable();
    }

    /*
    remove semi colon from the line helper method
     */
    private String removeSemiColon(String line) throws MissingSemiColon {
        line = line.trim();
        checkSemiColon(line);
        line = line.substring(0, line.length() - 1);
        return line;
    }

    /*
    check that semi colon exists in line
     */
    private void checkSemiColon(String line) throws MissingSemiColon {
        // line is trimmed from end and front.
        Matcher endsWithSemiColon = Verifier.ENDS_WITH_COLON_REGEX.matcher(line);
        if (!endsWithSemiColon.matches()) {
            throw new MissingSemiColon();
        }
    }

    /**
     * getter for locals created in the current block
     *
     * @return array list of variablres
     */
    public ArrayList<Variable> getBlockLocals() {
        return this.blockLocals;
    }
}

