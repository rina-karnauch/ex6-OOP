package oop.ex6.verifier;

import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * wrapper class of local variables
 *
 * @author rina.karnauch edenkeider
 */
public class LocalsWrapper {


    /*
    local variable names
     */
    private HashSet<String> names;

    /*
   global variable lines
    */
    private ArrayList<Boolean> initialization;

    /*
    local variables
     */
    private ArrayList<Variable> variables;

    /**
     * constructor for wrapper class of local variables variable
     *
     * @param locals local variables array list
     * @param names  hash set of all variable names
     * @param hasValues array list of has value fields of each variable
     */
    public LocalsWrapper(ArrayList<Variable> locals, HashSet<String> names, ArrayList<Boolean> hasValues) {
        this.variables = locals;
        this.names = names;
        this.initialization = hasValues;
    }

    /**
     * constructor for wrapper class of local variables variable
     */
    public LocalsWrapper() {
        this.variables = new ArrayList<Variable>();
        this.names = new HashSet<String>();
        this.initialization = new ArrayList<Boolean>();
    }

    /**
     * get variables out of the wrapper
     *
     * @return array list of variables.
     */
    ArrayList<Variable> getLocals() {
        return this.variables;
    }

    /**
     * get names out of the wrapper
     *
     * @return array list of variables.
     */
    HashSet<String> getNames() {
        return this.names;
    }

    /**
     * clear all the wrapper fields
     */
    void clear() {
        this.initialization.clear();
        this.names.clear();
        this.variables.clear();
    }

    /**
     * add a variable to locals
     *
     * @param var      variable to add
     * @param name     name to add
     * @param hasValue does it have a value or not
     */
    void add(Variable var, String name, Boolean hasValue) {
        this.variables.add(var);
        this.names.add(name);
        this.initialization.add(hasValue);
    }

    /**
     * add all variables from the given collection
     *
     * @param variables variable collection to add
     */
    void addAll(ArrayList<Variable> variables) {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        for (Variable v : variables) {
            boolean hasValue = v.hasValue();
            String name = v.getName();
            add(v, name, hasValue);
        }
    }

    /**
     * get variables of certain depth from locals
     *
     * @param depth certain depth to get from
     * @return array list of variables of certain depth
     */
    ArrayList<Variable> getCertainDepth(int depth) {
        // we receive a depth of a block,
        // we want to get all the variables that we can use in that depth.
        // so we need variables to be outside this depth- or inside of it.
        ArrayList<Variable> fittingDepthVariables = new ArrayList<Variable>();
        for (Variable var : this.variables) {
            if (depth >= var.getDepth()) {
                fittingDepthVariables.add(var);
            }
        }
        return fittingDepthVariables;
    }
}
