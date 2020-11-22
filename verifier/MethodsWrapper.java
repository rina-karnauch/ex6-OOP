package oop.ex6.verifier;

import oop.ex6.method.Method;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * class wrapper to method informations
 *
 * @author rina.karnauch edenkeidar
 */
public class MethodsWrapper {


    /*
    names of methods
     */
    private HashSet<String> names;

    /*
    array list of methods themselves
     */
    private ArrayList<Method> methods;

    /*
    lines of method declarations
     */
    private ArrayList<Integer> lines;

    /*
    the blocks themselves of the methods
     */
    private ArrayList<ArrayList<String>> blocks;

    /*
    globals for all methods
     */
    private GlobalsWrapper globals;

    /**
     * constructor for methods wrapper
     *
     * @param methods the methods list
     * @param names   the names
     * @param lines   the lines
     * @param blocks  the blocks
     */
    public MethodsWrapper(ArrayList<Method> methods, HashSet<String> names, ArrayList<Integer> lines,
                          ArrayList<ArrayList<String>> blocks) {
        this.methods = methods;
        this.names = names;
        this.lines = lines;
        this.blocks = blocks;
    }

    /**
     * constructor for a methods wrapper
     */
    public MethodsWrapper() {
        this.methods = new ArrayList<Method>();
        this.names = new HashSet<String>();
        this.lines = new ArrayList<Integer>();
        this.blocks = new ArrayList<ArrayList<String>>();
    }

    /**
     * constructor for a methods wrapper,
     *
     * @param globals globals for all methods
     */
    public MethodsWrapper(GlobalsWrapper globals) {
        this.methods = new ArrayList<Method>();
        this.names = new HashSet<String>();
        this.lines = new ArrayList<Integer>();
        this.blocks = new ArrayList<ArrayList<String>>();
        this.globals = globals;
    }

    /**
     * clear all information in methods wrapper
     */
    public void clear() {
        this.methods.clear();
        this.names.clear();
        this.lines.clear();
        this.blocks.clear();
    }

    /**
     * add a method to the methods wrapper
     *
     * @param method the method
     * @param name   the name of the method
     * @param line   line of declaration
     * @param block  the block of the method
     */
    public void add(Method method, String name, Integer line, ArrayList<String> block) {
        this.methods.add(method);
        this.names.add(name);
        this.lines.add(line);
        this.blocks.add(block);
    }

    /**
     * get number of methods in code
     *
     * @return number of current methods in sjava file
     */
    public int size() {
        return this.methods.size();
    }

    /**
     * get all methods names
     *
     * @return hash set of names
     */
    public HashSet<String> getMethodNames() {
        return this.names;
    }

    /**
     * get all methods themselves
     *
     * @return array list of methods
     */
    public ArrayList<Method> getMethods() {
        return this.methods;
    }


    /**
     * get block of method by index of insertion of the method
     *
     * @param i the index
     * @return a block of the method of index i in the sjava code
     */
    public ArrayList<String> getBlockByIndex(int i) {
        return this.blocks.get(i);
    }

    /**
     * getter of all blocks of methods
     *
     * @return array list of blocks(blocks is an array list of string)
     */
    public ArrayList<ArrayList<String>> getBlocks() {
        return this.blocks;
    }

    /**
     * getter of method by index of insertion
     *
     * @param i index
     * @return method numbered i in sjava code
     */
    public Method getMethodByIndex(int i) {
        return this.methods.get(i);
    }

    /**
     * contains method to check if method with such name exists
     *
     * @param name the name of method
     * @return true for exists false otherwise
     */
    public boolean contains(String name) {
        return this.names.contains(name);
    }

    /**
     * getter for method by name
     *
     * @param name name of method
     * @return method if exists or null.
     */
    public Method getMethod(String name) {
        if (name == null || name.isEmpty() || !contains(name)) {
            return null;
        }
        for (Method m : methods) {
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    /**
     * getter for all globals for all methods
     * @return globals wrapper of all globals in the code file
     */
    public GlobalsWrapper getGlobals() {
        return this.globals;
    }

}
