package oop.ex6.verifier;

import oop.ex6.variable.Variable;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * class of wrapper of globals according to a certain code block.
 * variables might be globals for a code block but actually local(means they are in an outer block of the current block)
 *
 * @author rina.karnauch  edenkeider
 */
public class GlobalsWrapper {

    /*
    global variable names
     */
    private HashSet<String> names;

    /*
   global variable lines
    */
    private ArrayList<Integer> depths;

    /*
    global variables
     */
    private ArrayList<Variable> variables;

    /*
    global variable lines
     */
    private ArrayList<Integer> lines;

    /**
     * constructor for wrapper class of a global variable
     *
     * @param globals global variables array list
     * @param names   hash set of all variable names
     * @param depths  line of globals
     * @param lines   line numbers
     */
    public GlobalsWrapper(ArrayList<Variable> globals, HashSet<String> names, ArrayList<Integer> depths,
                          ArrayList<Integer> lines) {
        this.variables = globals;
        this.names = names;
        this.depths = depths;
        this.lines = lines;
    }

    /**
     * constructor for creating a wrapper.
     */
    public GlobalsWrapper() {
        this.variables = new ArrayList<Variable>();
        this.names = new HashSet<String>();
        this.depths = new ArrayList<Integer>();
        this.lines = new ArrayList<Integer>();
    }

    /**
     * getter of  all variables of globals in wrapper
     *
     * @return array list of variables.
     */
    ArrayList<Variable> getGlobals() {
        return this.variables;
    }

    /**
     * getter of  all depths of globals in wrapper
     *
     * @return array list of depths.
     */
    ArrayList<Integer> getDepths() {
        return this.depths;
    }

    /**
     * getter of  all names of globals in wrapper
     *
     * @return hash set of names
     */
    HashSet<String> getNames() {
        return this.names;
    }

    /**
     * method to clear all the wrapper.
     */
    void clear() {
        this.depths.clear();
        this.names.clear();
        this.variables.clear();
        this.lines.clear();
    }

    /**
     * method to add single var to the wrapper
     *
     * @param var variable
     */
    void add(Variable var) {
        this.variables.add(var);
        this.names.add(var.getName());
        this.depths.add(var.getDepth());
        this.lines.add(var.getLine());
    }

    /**
     * all all from the array list of variables to the wrapper
     *
     * @param collection the variables to add
     */
    void addAll(ArrayList<Variable> collection) {
        for (Variable var : collection) {
            this.variables.add(var);
            this.names.add(var.getName());
            this.depths.add(var.getDepth());
            this.lines.add(var.getLine());

        }
    }

    /**
     * method to get a varaible from the wrapper by its name
     *
     * @param name name of variable
     * @return variable with that name
     */
    Variable get(String name) {
        if (this.names.contains(name)) {
            for (Variable var : this.variables) {
                if (name.equals(var.getName())) {
                    return var;
                }
            }
        }
        return null;
    }

    /**
     * contains method for a string name
     *
     * @param name name of variable to check if exists
     * @return true for contained, false otherwise
     */
    boolean contains(String name) {
        return this.names.contains(name);
    }
}
