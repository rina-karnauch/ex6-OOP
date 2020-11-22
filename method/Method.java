package oop.ex6.method;

import oop.ex6.variable.Variable;

import java.util.ArrayList;

/**
 * class of an object representing a method in the code
 *
 * @author rina.karnauch, edenkeider
 */
public class Method {

    /**
     * enum of return types possibilities for methods
     * created so we could add possible return types to our methods,
     * and make our code with the open-close design idea- close to changes and open to new functionalities.
     */
    enum returnTypes {
        VOID("void");

        private final String typeName;

        returnTypes(String s) {
            this.typeName = s;
        }

        @Override
        public String toString() {
            return this.typeName;
        }
    }

    /*
    name of method field
     */
    private final String name;

    /*
    array list of parameters given to the method
     */
    private ArrayList<Variable> parameters = new ArrayList<Variable>();

    /*
    return type of the method
     */
    private returnTypes returnType;

    /*
    line number of the signature of the method.
     */
    private int declarationLine;

    /*
    parameters given to method amount
     */
    private int paramAmount;

    /**
     * constructor for a method object
     *
     * @param methodName name of method
     * @param returnType return type
     * @param parameters parameters array list
     * @param line line we call method from
     * @throws IllegalReturnValue illegal return value for a method
     */
    public Method(String methodName, String returnType, ArrayList<Variable> parameters, int line)
            throws IllegalReturnValue {

        constructorSetReturnType(returnType);
        this.name = methodName;
        this.parameters = parameters;
        this.paramAmount = parameters.size();
        this.declarationLine = line;
    }

    /*
    a helper method to construct the return type of a method and check if its valid
     */
    private void constructorSetReturnType(String returnType) throws IllegalReturnValue {
        for (returnTypes r : returnTypes.values()) {
            if (returnType.equals(r.toString())) {
                this.returnType = r;
            }
        }

        if (this.returnType == null) {
            throw new IllegalReturnValue();
        }
    }

    /**
     * getter method for a method's name
     *
     * @return string of name of the current method
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter method for method's declaration parameters
     *
     * @return array list of variable of method parameters
     */
    public ArrayList<Variable> getMethodParameters() {
        return this.parameters;
    }

    /**
     * getter for method's parameters amount
     *
     * @return int of amount of parameters the method should recive
     */
    public int getParamAmount() {
        return this.paramAmount;
    }
}
