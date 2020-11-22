package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class of condition verifier which implement the verifier function interface and tests out a variable.
 *
 * @author rina.karnauch, edenkeider
 */
public abstract class VariableVerifier implements Verifier {

    protected GlobalsWrapper globals = new GlobalsWrapper();

    /**
     * enum of a verified types we are allowed in our code
     */
    public enum VerifiedTypes {
        INT(INT_TYPE) {
            @Override
            public HashSet<VerifiedTypes> getConvertibles() {
                INT.convertibles.add(INT);
                return INT.convertibles;
            }
        }, DOUBLE(DOUBLE_TYPE) {
            @Override
            public HashSet<VerifiedTypes> getConvertibles() {
                DOUBLE.convertibles.add(INT);
                DOUBLE.convertibles.add(DOUBLE);
                return DOUBLE.convertibles;
            }

        }, STRING(STRING_TYPE) {
            @Override
            public HashSet<VerifiedTypes> getConvertibles() {
                STRING.convertibles.add(STRING);
                return STRING.convertibles;
            }
        }, BOOLEAN(BOOLEAN_TYPE) {
            @Override
            public HashSet<VerifiedTypes> getConvertibles() {
                BOOLEAN.convertibles.add(INT);
                BOOLEAN.convertibles.add(DOUBLE);
                BOOLEAN.convertibles.add(BOOLEAN);
                return BOOLEAN.convertibles;
            }
        }, CHAR(CHAR_TYPE) {
            @Override
            public HashSet<VerifiedTypes> getConvertibles() {
                CHAR.convertibles.add(CHAR);
                return CHAR.convertibles;
            }
        };

        /*
        name of type
         */
        private String typeName;

        /*
        hash set of possible types the current type may receive and contain.
         */
        private HashSet<VerifiedTypes> convertibles;

        /**
         * constructor for the enum
         *
         * @param s the type name
         */
        VerifiedTypes(String s) {
            this.typeName = s;
            this.convertibles = new HashSet<VerifiedTypes>();
        }

        /**
         * to string method override
         *
         * @return string name of the enum
         */
        @Override
        public String toString() {
            return this.typeName;
        }

        /**
         * abstract method to get the types which can be contained inside current type which is checked
         *
         * @return hash set of convertibles
         */
        public abstract HashSet<VerifiedTypes> getConvertibles();

        /**
         * a getter method to get a verified type out of a string given to the method, and the line we get the string
         * from
         *
         * @param type string of type
         * @return the verified type
         * @throws unsupportedType exception of unsupported type in sjava file
         */
        public static VerifiedTypes getVerifiedType(String type) throws unsupportedType {
            for (VerifiedTypes t : VerifiedTypes.values()) {
                if (type.equals(t.toString())) {
                    return t;
                }
            }
            throw new unsupportedType();
        }

        /**
         * method to check equality between two verified types
         *
         * @param other the other type
         * @return true for equal types, false for not.s
         */
        public boolean equals(VerifiedTypes other) {
            String first = this.toString();
            String second = other.toString();
            return first.equals(second);
        }

        // only the assigned value is given
        public static VerifiedTypes getTypeOfString(String assignment) throws InconvertibleTypeAssignment {
            Matcher formatMatcher = INT_FORMAT.matcher(assignment);
            if (formatMatcher.matches()) {
                return INT;
            }
            formatMatcher = DOUBLE_FORMAT.matcher(assignment);
            if (formatMatcher.matches()) {
                return DOUBLE;
            }
            formatMatcher = CHAR_FORMAT.matcher(assignment);
            if (formatMatcher.matches()) {
                return CHAR;
            }
            formatMatcher = STRING_FORMAT.matcher(assignment);
            if (formatMatcher.matches()) {
                return STRING;
            }
            formatMatcher = BOOLEAN_FORMAT.matcher(assignment);
            if (formatMatcher.matches()) {
                return BOOLEAN;
            }
            throw new InconvertibleTypeAssignment();
        }

        public void checkConversation(VerifiedTypes toInsert) throws InconvertibleTypeAssignment {
            if (!this.getConvertibles().contains(toInsert)) {
                throw new InconvertibleTypeAssignment();
            }
        }
    }

    /**
     * method of the functional interface to test section of the code, each method which implements the verifier
     * will test out by a different strategy according to the code section we check.
     *
     * @throws MissingVariableName           variable name not given
     * @throws UndeclaredVariableType        variable type not given
     * @throws UnsupportedCommand            unknown command in sjava file
     * @throws InconvertibleTypeAssignment   type given does not match assignment
     * @throws ValueToFinalVariableException value trying to assign to a final var
     * @throws VariableNameTaken             variable name is taken already
     * @throws UnexpectedValueToVariable     no matching type given to variable value
     * @throws MissingValueInVariable        missing value in assign to variable
     * @throws SyntaxError                   syntax error in some line
     */
    @Override
    public abstract void test() throws SyntaxError, UnsupportedCommand, ValueToFinalVariableException,
            VariableNameTaken, UnexpectedValueToVariable, MissingValueInVariable, MissingVariableName,
            UndeclaredVariableType, InconvertibleTypeAssignment, UnexpectedValueToMethodSignature, MissingParenthesisException, IllegalReturnValue, MissingMethodName, MethodNameTaken, UnsupportedCommandInMethod, UnexpectedParameterInMethodSignature, ReturnMissingAtEndOfMethod, MissingMethodParameters, MissingMethodReturnType;

    /**
     * a method to handle a given line variable
     *
     * @param isGlobal     boolean indicator weather a variable is global or not
     * @param variableLine the line to be interpreted
     * @return array list of new variables
     * @throws UndeclaredVariableType        unwanted type in sjava file
     * @throws UnexpectedValueToVariable     value not qualified for such type of variable
     * @throws ValueToFinalVariableException value not given to final variable
     * @throws unsupportedType               unsupported type to a variable
     * @throws MissingValueInVariable        missing value inside a variable when = exists.
     * @throws MissingVariableName           no name for variable
     * @throws VariableNameTaken             name is already taken by another variable
     */
    ArrayList<Variable> handleVariableLine(Boolean isGlobal, String variableLine, int depth, int line)
            throws UndeclaredVariableType, UnexpectedValueToVariable, ValueToFinalVariableException, unsupportedType,
            MissingValueInVariable, MissingVariableName, VariableNameTaken, InconvertibleTypeAssignment {
        ArrayList<Variable> checkedVars = new ArrayList<Variable>();
        boolean isFinal = false;
        String type;

        Matcher finalityMatcher = Verifier.FINALITY_REGEX.matcher(variableLine);
        if (finalityMatcher.lookingAt()) {
            isFinal = true;
            variableLine = Verifier.cutAndTrimToEnd(finalityMatcher, 1, variableLine);
        }

        Matcher typeCastMatcher = Verifier.TYPE_REGEX.matcher(variableLine);
        if (typeCastMatcher.lookingAt()) {
            type = variableLine.substring(typeCastMatcher.start(), typeCastMatcher.end());
            variableLine = Verifier.cutAndTrimToEnd(typeCastMatcher, 0, variableLine);
        } else {
            throw new UndeclaredVariableType();
        }

        String[] variablesOfLine = variableLine.split(Verifier.COMMA, -1);

        for (String singleVar : variablesOfLine) {
            singleVar = singleVar.trim(); // no spaces at beginning and end
            Variable variable = handleSingleVariable(isGlobal, isFinal, type, singleVar, depth, line);
            checkedVars.add(variable);
        }
        return checkedVars;
    }

    /**
     * method to handle a one variable information after cutting
     *
     * @param isGlobal            is the variable global
     * @param isFinal             is it final
     * @param type                the string of it's type
     * @param currentVariableLine current line to interpret
     * @return variable from the current substring
     * @throws MissingVariableName           no name given to variable
     * @throws ValueToFinalVariableException value changed to a final variable
     * @throws UnexpectedValueToVariable     outmatching value to another type of variable
     * @throws MissingValueInVariable        no value given after  =
     * @throws unsupportedType               no such type exists
     * @throws VariableNameTaken             variable name is already taken
     */
    Variable handleSingleVariable(Boolean isGlobal, Boolean isFinal, String type, String currentVariableLine,
                                  int depth, int line)
            throws MissingVariableName, ValueToFinalVariableException, UnexpectedValueToVariable,
            MissingValueInVariable, unsupportedType, VariableNameTaken, InconvertibleTypeAssignment {
        boolean hasValue = false;

        Matcher variableNameMatcher = Verifier.VARIABLE_NAME_REGEX.matcher(currentVariableLine);
        String name;
        String valueToInsert;
        if (variableNameMatcher.lookingAt()) {
            name = currentVariableLine.substring(variableNameMatcher.start(), variableNameMatcher.end());
            variableNameCheck(name, depth);
        } else {
            throw new MissingVariableName();
        }

        String assignment = EMPTY;
        currentVariableLine = Verifier.cutAndTrimToEnd(variableNameMatcher, 0, currentVariableLine);
        Matcher equalValueMatcher = VARIABLE_INITIALIZATIONS_REGEX.matcher(currentVariableLine);
        if (equalValueMatcher.lookingAt()) {
            hasValue = true;
            boolean paramFlag = false;
            currentVariableLine = Verifier.cutAndTrimToEnd(equalValueMatcher, 0, currentVariableLine);
            valueToInsert = currentVariableLine;
            if (globals.contains(valueToInsert)) {
                Variable toCapture = getVariable(valueToInsert);
                if (toCapture != null) {
                    valueToInsert = toCapture.getAssigned();
                    paramFlag = toCapture.isParam();
                }
            }
            if (!paramFlag && (valueToInsert == null || valueToInsert.isEmpty())) {
                throw new MissingValueInVariable();
            }
            assignment = valueToInsert;
        } else {
            if (isFinal) {
                throw new ValueToFinalVariableException();
            } else if (!currentVariableLine.isEmpty()) {
                throw new UnexpectedValueToVariable();
            }
        }
        // not from a method
        Variable var = new Variable(type, name, isFinal, hasValue, depth, line, assignment, false);
        if (isGlobal) {
            globals.add(var);
        }
        return var;
    }

    /*
    get variable out of name of current globals
     */
    private Variable getVariable(String name) {
        if (!globals.contains(name)) {
            return null;
        }
        for (Variable var : globals.getGlobals()) {
            if (name.equals(var.getName())) {
                return var;
            }
        }
        return null;
    }

    /**
     * abstract method to check the variable name
     *
     * @param name  name we would like to create
     * @param depth depth of name to check
     * @throws VariableNameTaken name of variable taken
     */
    protected abstract void variableNameCheck(String name, int depth) throws VariableNameTaken;

}
