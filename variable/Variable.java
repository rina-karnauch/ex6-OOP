package oop.ex6.variable;

import oop.ex6.verifier.VariableVerifier.*;

/**
 * class represents a variable in our sjava code
 *
 * @author edenkeider, rina.karnauch
 */
public class Variable {

    /*
    field to determine weather variable is final or not
     */
    private boolean isFinal;

    /*
    name that was set to the variable
     */
    private String name;

    /*
    field to determine weather variable was initialized with a value or not
     */
    private boolean hasValue;

    /*
    type of variable
     */
    private final VerifiedTypes type;

    /*
    block the variable is in
     */
    private int depth;

    /*
    line of variable
     */
    private int line;

    /*
    assigned value
     */
    private String assigned;

    /*
    flag to indicate if value is a parameter from method line or not.
     */
    private boolean isParam;


    /**
     * constructor for variable object
     *
     * @param line       line number of line
     * @param depth      depth of variable, in which block it lives
     * @param type       type of variable, is an enum of verified type
     * @param name       name of variable
     * @param isFinal    boolean of weather the variable is final or not, true for final, false for not.
     * @param hasValue   boolean of weather the variable has a value or not, true for has, false for doesn't,
     * @param isParam    boolean flag to weather a variable is a param from a method
     * @param assignment assigned value
     * @throws unsupportedType             unsupported type given to variable
     * @throws InconvertibleTypeAssignment inconvertible assignment to variable
     */
    public Variable(String type, String name, boolean isFinal, boolean hasValue, int depth, int line, String assignment
            , boolean isParam)
            throws unsupportedType, InconvertibleTypeAssignment {

        this.name = name;
        this.type = VerifiedTypes.getVerifiedType(type.trim());
        this.isFinal = isFinal;
        this.hasValue = hasValue;
        this.depth = depth;
        this.line = line;
        this.isParam = isParam;
        if (assignment != null && !assignment.isEmpty()) {
            checkAssignment(assignment);
        }
    }

    /*
    method to check assignment into a variable.
     */
    private void checkAssignment(String assignment) throws InconvertibleTypeAssignment, unsupportedType {
        VerifiedTypes assignmentType = VerifiedTypes.getTypeOfString(assignment);
        this.type.checkConversation(assignmentType);
        this.assigned = assignment;
    }

    /**
     * get name of variable getter.
     *
     * @return string of the variabales name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * boolean getter method to check if variable is final.
     *
     * @return true for final, false otherwise.
     */
    public Boolean isFinal() {
        return this.isFinal;
    }

    /**
     * get type of variable getter.
     *
     * @return type of variable, a VariableType nested class type.
     */
    public VerifiedTypes getType() {
        return this.type;
    }

    /**
     * boolean getter method to check if variable was initialized.
     *
     * @return true for has been, false otherwise.
     */
    public Boolean hasValue() {
        return this.hasValue;
    }

    /**
     * getter method for variables depth. the bigger the depth the deeper the value.
     *
     * @return int of depth
     */
    public int getDepth() {
        return this.depth;
    }

    public int getLine() {
        return this.line;
    }

    public String getAssigned() {
        return this.assigned;
    }

    /**
     * a setter method to the hasValue field.
     *
     * @param booleanSet boolean value to set to the hasValue field
     * @throws ValueToFinalVariableException exception of trying to change a final variable.
     */
    public void setValue(boolean booleanSet) throws ValueToFinalVariableException {
        if (!isFinal) {
            this.hasValue = booleanSet;
        } else {
            throw new ValueToFinalVariableException();
        }
    }

    /**
     * method to check if variable is a method parameter
     *
     * @return true if yes false otherwise
     */
    public boolean isParam() {
        return this.isParam;
    }
}
