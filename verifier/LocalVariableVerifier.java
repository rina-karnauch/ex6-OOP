package oop.ex6.verifier;

import oop.ex6.variable.*;

import java.util.ArrayList;

public class LocalVariableVerifier extends VariableVerifier implements Verifier {

    /*
    the line to interpret locals from
     */
    private String lineOfVariables;

    /*
    array list of the variables declared in the current line we verify
     */
    private ArrayList<Variable> localVariables;

    /*
    depth of block where current line to check is at
     */
    private int lineDepth;

    private static final int UNIMPORTANT_LINE_NUMBER = -1;

    /**
     * constructor for the variable verifier
     * @param lineOfVariables lines of variable to read
     * @param depth depth of block
     * @param globalWrapper globals to use in wrapper
     * @param locals locals to use
     */
    public LocalVariableVerifier(String lineOfVariables, int depth,
                                 GlobalsWrapper globalWrapper, ArrayList<Variable> locals) {
        this.lineOfVariables = lineOfVariables;
        this.localVariables = locals;
        this.lineDepth = depth;
        this.globals = globalWrapper;
        this.globals.addAll(this.localVariables);
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
    public void test() throws unsupportedType, VariableNameTaken,
            ValueToFinalVariableException, UnexpectedValueToVariable, MissingValueInVariable,
            MissingVariableName, UndeclaredVariableType, InconvertibleTypeAssignment {
        String variable = this.lineOfVariables.trim(); // no spaces at end
        this.localVariables = handleVariableLine(false, variable, lineDepth, UNIMPORTANT_LINE_NUMBER);
    }

    /**
     * abstract method to check the variable name
     *
     * @param name name we would like to create
     */
    @Override
    protected void variableNameCheck(String name, int blockDepth) throws VariableNameTaken {
        for (Variable v : localVariables) {
            if (name.equals(v.getName()) && blockDepth == v.getDepth()) {
                throw new VariableNameTaken();
            }
        }

        if (globals.contains(name) && globals.get(name).isParam()) {
            int gDepth = globals.get(name).getDepth();
            if (gDepth >= blockDepth) {
                throw new VariableNameTaken();
            }
        }
    }

    /**
     * get array list of verified locals from current local variable check
     * @return array list of verified variables
     */
    public ArrayList<Variable> getVerified() {
        return this.localVariables;
    }
}
