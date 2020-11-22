package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.variable.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * class of parameter line of method call verifier
 *
 * @author edenkeider rina.karnauch
 */
public class ParameterLineVerifier implements Verifier {

    /*
    parameter line to check
     */
    private String parameterLine;

    /*
    verified parameters by order
     */
    private ArrayList<Variable> verifiedParams;

    /*
    hash set of verified param names
     */
    private HashSet<String> verifiedNames;

    /*
    depth of signature parameters.
     */
    private final static int SIGNATURE_PARAM_DEPTH = 1;

    /*
    parameter line default line count
     */
    private final static int PARAMETERS_LINE = -1;


    /**
     * constructor for the parameters line in a method signature , verifier
     *
     * @param line line to verify
     */
    public ParameterLineVerifier(String line) {
        this.parameterLine = line;
        this.verifiedParams = new ArrayList<Variable>();
        this.verifiedNames = new HashSet<String>();
    }

    /**
     * method of the functional interface to test section of the code, each method which implements the verifier
     * will test out by a different strategy according to the code section we check.
     *
     * @throws UnexpectedParameterInMethodSignature given parameter to method call is not wanted
     * @throws MissingVariableName                  variable name not given
     * @throws UndeclaredVariableType               variable type not given
     * @throws InconvertibleTypeAssignment          type given does not match assignment
     */
    @Override
    public void test() throws UnexpectedParameterInMethodSignature, MissingVariableName,
            UndeclaredVariableType, unsupportedType, InconvertibleTypeAssignment, VariableNameTaken {
        this.parameterLine = this.parameterLine.trim();
        if (!this.parameterLine.isEmpty()) {
            String[] params = parameterLine.split(Verifier.COMMA);
            handleParams(params);
        }
    }

    /*
    method to handle all parameters given to the methods signature
     */
    private void handleParams(String[] params) throws UnexpectedParameterInMethodSignature,
            UndeclaredVariableType, unsupportedType, MissingVariableName, InconvertibleTypeAssignment,
            VariableNameTaken {
        for (String singleParam : params) {
            singleParam = singleParam.trim(); // no spaces at end and at start.
            handleSingleParam(singleParam);
        }
    }


    /*
    method to check if single param is declared correctly in method parameters signature
        // what we will need: String type, String name, boolean isFinal, boolean hasValue, int depth
        // method signature depth is 1- not global, but afterwards.
     */
    private void handleSingleParam(String param) throws UndeclaredVariableType, MissingVariableName,
            UnexpectedParameterInMethodSignature, unsupportedType, InconvertibleTypeAssignment, VariableNameTaken {
        boolean isFinal = false;
        String type;
        String name;
        Matcher finalParamMatcher = Verifier.FINALITY_REGEX.matcher(param);
        if (finalParamMatcher.lookingAt()) {
            isFinal = true;
            param = Verifier.cutAndTrimToEnd(finalParamMatcher, 0, param);
        }

        Matcher typeParamMatcher = Verifier.TYPE_REGEX.matcher(param);
        if (typeParamMatcher.lookingAt()) {
            type = param.substring(typeParamMatcher.start(), typeParamMatcher.end());
            param = Verifier.cutAndTrimToEnd(typeParamMatcher, 0, param);
        } else {
            throw new UndeclaredVariableType();
        }

        Matcher nameParamMatcher = Verifier.VARIABLE_NAME_REGEX.matcher(param);
        if (nameParamMatcher.lookingAt()) {
            name = param.substring(nameParamMatcher.start(), nameParamMatcher.end());
            if(verifiedNames.contains(name)){
                throw new VariableNameTaken();
            }
            param = Verifier.cutAndTrimToEnd(nameParamMatcher, 0, param);
        } else {
            throw new MissingVariableName();
        }

        if (!param.isEmpty()) {
            throw new UnexpectedParameterInMethodSignature();
        }
        // from a method so true at end
        Variable verifiedParam = new Variable(type, name, isFinal, true, SIGNATURE_PARAM_DEPTH,
                PARAMETERS_LINE, EMPTY, true);
        // always true because we always send a value to it.
        verifiedParams.add(verifiedParam);
        verifiedNames.add(name);
    }

    /**
     * getter method to get the parameters created by the line
     *
     * @return array list of parameters
     */
    ArrayList<Variable> getParameters() {
        return this.verifiedParams;
    }
}
