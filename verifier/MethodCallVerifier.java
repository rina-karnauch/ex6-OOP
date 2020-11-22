package oop.ex6.verifier;

import oop.ex6.method.Method;
import oop.ex6.variable.InconvertibleTypeAssignment;
import oop.ex6.variable.Variable;

import java.util.ArrayList;

/**
 * class of condition verifier which implement the verifier function interface and tests out a method call.
 *
 * @author rina.karnauch, edenkeider
 */
public class MethodCallVerifier implements Verifier {

    /*
    the method we are calling
     */
    private Method method;

    /*
    the param list we received for the call
     */
    private String params;

    /*
    variables we can use at this depth of block
     */
    private ArrayList<Variable> variablesToUse;

    /**
     * constructor for a method call verifier in the sjava code file lines
     *
     * @param method    the method we are calling
     * @param inputLine the input line we received which contains the parameters for the method
     * @param toUse variable to use inside method call
     */
    public MethodCallVerifier(Method method, String inputLine, ArrayList<Variable> toUse) {
        this.method = method;
        this.params = inputLine;
        this.variablesToUse = toUse;
    }

    @Override
    public void test() throws OutmatchingParametersToMethodCall, MethodCallWithNonexistentParameter,
            OutmatchingParameterType, InconvertibleTypeAssignment {
        checkForEmptyLine();
        if (!method.getMethodParameters().isEmpty()) {
            String[] paramArray = this.params.split(Verifier.COMMA, -1);
            checkParams(paramArray);
        }
    }

    /*
    method to check for empty line in parameters given method
     */
    private void checkForEmptyLine() throws OutmatchingParametersToMethodCall {
        if (this.params.isEmpty() && method.getParamAmount() != 0) {
            throw new OutmatchingParametersToMethodCall();
        } else if (method.getParamAmount() == 0 && !this.params.isEmpty()) {
            throw new OutmatchingParametersToMethodCall();
        }
    }

    /*
    method to check params to a method call
     */
    private void checkParams(String[] params) throws MethodCallWithNonexistentParameter,
            OutmatchingParametersToMethodCall, OutmatchingParameterType, InconvertibleTypeAssignment {
        ArrayList<Variable> methodParams = this.method.getMethodParameters();
        if (params.length != methodParams.size()) {
            // not enough params somewhere, or more than enough.
            throw new OutmatchingParametersToMethodCall();
        }
        int i = 0;
        for (String toBeParam : params) {
            toBeParam = toBeParam.trim();
            Variable toBeVariable = findParamByName(toBeParam);
            VariableVerifier.VerifiedTypes type = methodParams.get(i).getType();
            if (toBeVariable == null) {
                VariableVerifier.VerifiedTypes typeOfString = VariableVerifier.VerifiedTypes.getTypeOfString(toBeParam);
                if (!type.getConvertibles().contains(typeOfString)) {
                    throw new MethodCallWithNonexistentParameter();
                }
            } else {
                checkTypes(toBeVariable, methodParams.get(i));
                checkValue(toBeVariable);
            }
            i++;
        }
    }

    /*
    method helper to check if the variable has a value when sent to method call.
     */
    private void checkValue(Variable toBeVariable) throws MethodCallWithNonexistentParameter {
        if (!toBeVariable.hasValue()) {
            throw new MethodCallWithNonexistentParameter();
        }
    }

    /*
   method helper to check if type sent to variable of call equals the correct type needed in method
    */
    private void checkTypes(Variable first, Variable second) throws OutmatchingParameterType {
        VariableVerifier.VerifiedTypes firstType = first.getType();
        VariableVerifier.VerifiedTypes secondType = second.getType();

        if (!firstType.equals(secondType)) {
            throw new OutmatchingParameterType();
        }
    }

    /*
    parameter finder in method call params
     */
    private Variable findParamByName(String paramName) {
        for (Variable var : this.variablesToUse) {
            if (paramName.equals(var.getName())) {
                return var;
            }
        }
        return null;
    }
}
