package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * class of condition verifier which implement the verifier function interface and tests out methods signatures
 *
 * @author rina.karnauch, edenkeider
 */
public class MethodSignatureVerifier implements Verifier {

    /*
    string of declaration of the method
     */
    private String declaration;

    /*
    method after verification
     */
    private Method verified;

    /*
    previously declared method names
     */
    private HashSet<String> previousMethodNames;

    /*
    line of declaration
     */
    private int line;

    /**
     * a constructor for the method signature verifier
     *
     * @param declaration         the deceleration line of the method
     * @param previousMethodNames previously declared method names
     * @param line                line number
     */
    public MethodSignatureVerifier(String declaration, HashSet<String> previousMethodNames, int line) {
        this.declaration = declaration;
        this.line = line;
        this.previousMethodNames = previousMethodNames;
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
     * @throws InconvertibleTypeAssignment          type given does not match assignment
     */
    @Override
    public void test() throws MissingMethodReturnType, IllegalReturnValue,
            MissingMethodName, MissingMethodParameters, UnexpectedValueToMethodSignature, MethodNameTaken,
            UnexpectedParameterInMethodSignature, MissingVariableName, UndeclaredVariableType, unsupportedType,
            InconvertibleTypeAssignment, VariableNameTaken {
        String declarationLine = this.declaration;
        declarationLine = declarationLine.trim(); // no spaces.

        String returnValue;
        String methodName;
        String parametersLine;
        ArrayList<Variable> methodParameters;

        Matcher returnValueMatcher = RETURN_VALUE_REGEX.matcher(declarationLine);
        if (returnValueMatcher.lookingAt()) {
            returnValue = declarationLine.substring(returnValueMatcher.start(), returnValueMatcher.end());
            declarationLine = Verifier.cutAndTrimToEnd(returnValueMatcher, 1, declarationLine);
        } else {
            throw new MissingMethodReturnType();
        }

        Matcher methodNameMatcher = METHOD_NAME_REGEX.matcher(declarationLine);
        if (methodNameMatcher.lookingAt()) {
            methodName = declarationLine.substring(methodNameMatcher.start(), methodNameMatcher.end());
            checkMethodName(methodName);
            declarationLine = Verifier.cutAndTrimToEnd(methodNameMatcher, 0, declarationLine);
        } else {
            throw new MissingMethodName();
        }

        Matcher methodParamsMatcher = METHOD_PARAM_REGEX.matcher(declarationLine);
        if (methodParamsMatcher.lookingAt()) {
            parametersLine = declarationLine.substring(methodParamsMatcher.start() + 1, methodParamsMatcher.end() - 1);
            methodParameters = getParametersOutOfLine(parametersLine);
            declarationLine = Verifier.cutAndTrimToEnd(methodParamsMatcher, 0, declarationLine);
        } else {
            throw new MissingMethodParameters();
        }
        if (!declarationLine.equals(CLOSING_METHOD_CHAR)) {
            throw new UnexpectedValueToMethodSignature();
        }
        this.verified = new Method(methodName, returnValue, methodParameters, line);
    }

    /*
    method to check the the method name doesn't already exist.
     */
    private void checkMethodName(String name) throws MethodNameTaken {
        for (String existingMethod : previousMethodNames) {
            if (existingMethod.equals(name)) {
                throw new MethodNameTaken();
            }
        }
    }

    /*
    method to create a methods parameters line
     */
    private ArrayList<Variable> getParametersOutOfLine(String parametersLine) throws
            UnexpectedParameterInMethodSignature, UndeclaredVariableType, unsupportedType, MissingVariableName,
            InconvertibleTypeAssignment, VariableNameTaken {
        ParameterLineVerifier paramsVerifier = new ParameterLineVerifier(parametersLine);
        paramsVerifier.test();
        return paramsVerifier.getParameters();
    }

    /**
     * getter for the verified method
     *
     * @return method with the information from the signature after verification
     */
    Method getVerified() {
        return this.verified;
    }

}
