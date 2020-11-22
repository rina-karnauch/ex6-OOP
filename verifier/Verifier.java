package oop.ex6.verifier;

import oop.ex6.method.*;
import oop.ex6.parsing.MissingParenthesisException;
import oop.ex6.variable.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * functional interface of a verifier, interface to test out a sample of the code and check
 * if it is correct
 *
 * @author rina.karnauch, edenkeider
 */
public interface Verifier {

    /**
     * to use in regex for variable types
     */
    static final String VARIABLE_TYPES = "(int|char|double|boolean|String)\\s+";

    /**
     * to use in regex for variable name
     */
    static final String VARIABLE_NAME = "([A-Za-z]|[_])+\\w*";

    /**
     * to use in regex for final declaration
     */
    static final String FINAL_VALUE = "(final\\s+)?";

    /**
     * to use in regex for assignment checking
     */
    static final String ASSIGNMENT = "\\s*=\\s*(.*)";

    /**
     * return regex to check if exists a return at end of method block
     */
    static final Pattern RETURN_REGEX = Pattern.compile("return\\s*;");

    /**
     * closing of code block regex
     */
    static final Pattern CLOSING_REGEX = Pattern.compile("\\}");

    /**
     * regexes for the conditions checking
     */

    /*
    regex for condition
     */
    static final Pattern CONDITION_REGEX = Pattern.compile("^(while|if)\\s*\\((.*)\\)\\s*\\{");

    /**
     * regex for a method call
     */
    static final Pattern METHOD_CALL_REGEX = Pattern.compile("\\s*(\\w+)\\s*\\((.*)\\)\\s*");

    /**
     * regex for final declaration
     */
    static final Pattern LOCAL_VARIABLE_DECLARATION_REGEX = Pattern.compile(FINAL_VALUE +
            VARIABLE_TYPES + VARIABLE_NAME);

    /**
     * regex for local variable declaration
     */
    static final Pattern LOCAL_VARIABLE_ASSIGNMENT_REGEX = Pattern.compile(FINAL_VALUE + VARIABLE_TYPES +
            VARIABLE_NAME + ASSIGNMENT);

    /**
     * regex for existing variable assignment declaration
     */
    static final Pattern EXISTING_VARIABLE_ASSIGNMENT_REGEX = Pattern.compile("(([A-Za-z]\\w*)(\\s*=\\s*)(.*))");

    /**
     * equal sign for assignment checking
     */
    static final String EQUAL_SIGN = "=";

    /**
     * regex for the method declaration finding
     */
    static final Pattern METHOD_REGEX = Pattern.compile("\\s*void\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*\\{$");

    /**
     * regex for the variable declaration finding
     */
    static final Pattern VARIABLE_REGEX =
            Pattern.compile("\\s*(final\\s+)?(int|char|double|String|boolean)\\s*[_]*[a-zA-Z]+(.*);$");


    /**
     * regex for existing variable assignment declaration
     */
    static final Pattern ASSIGNMENT_REGEX =
            Pattern.compile("(([A-Za-z]\\w*)(\\s*=\\s*)(.*))");

    /**
     * empty line
     */
    static final Pattern EMPTY_LINE = Pattern.compile("\\s*");

    /**
     * regex for the comments finding
     */
    static final Pattern COMMENT_REGEX = Pattern.compile("^\\/\\/(.*)");

    /**
     * check that a line ends with a comma
     */
    static final Pattern ENDS_WITH_COLON_REGEX = Pattern.compile("(.*);$"); // ends with a ";"

    /**
     * regex for a variable or parameter which is final,
     */
    static final Pattern FINALITY_REGEX = Pattern.compile("final"); // final 0 or 1 times,
    // and so we dont "finalint a=5;"

    /**
     * regex for the type of variable
     */
    static final Pattern TYPE_REGEX = Pattern.compile("(int|char|double|String|boolean)\\s"); // one of
    // those words, and there must be a space afterwards so we don't have "inta =5;"

    /**
     * regex for the variable name correctness
     */
    static final Pattern VARIABLE_NAME_REGEX = Pattern.compile("[_]*([a-zA-Z])+\\w*"); // at least one a-zA-Z and
    // then we are allowed to use [A-Za-z0-9_].

    /**
     * comma for the variable cutting
     */
    static final String COMMA = ",";

    /**
     * separator to split the param line in the condition
     */
    static final String SEPARATOR = "(\\&\\&)|(\\|\\|)";

    /**
     * regex for possible value inside condition
     */
    static final Pattern BOOLEAN_REGEX = Pattern.compile("-?\\d+(.\\d+)?|true|false");

    /**
     * closing bracket for condition
     */
    static final String CLOSING_BRACKET = "}";

    /**
     * opening bracket for condition
     */
    static final String OPENING_BRACKET = "{";


    /**
     * regex to look up for the return value, comes before anything at the method declaration
     */
    static final Pattern RETURN_VALUE_REGEX = Pattern.compile("^\\S*"); // anything which is not a
    // space from the beginning

    /**
     * regex for a method name, mut start with a letter.
     */
    static final Pattern METHOD_NAME_REGEX = Pattern.compile("[a-zA-Z]+\\w*");

    /**
     * regex for a method name, mut start with a letter.
     */
    static final Pattern METHOD_PARAM_REGEX = Pattern.compile("\\(.*\\)");

    /**
     * closing bracket
     */
    static final String CLOSING_METHOD_CHAR = "{";


    /**
    int type string format
     */
    final static String INT_TYPE = "int";

    /**
    double type string format
     */
    final static String DOUBLE_TYPE = "double";

    /**
    string type string format
     */
    final static String STRING_TYPE = "String";

    /**
    boolean type string format
     */
    final static String BOOLEAN_TYPE = "boolean";

    /**
    char type string format
     */
    final static String CHAR_TYPE = "char";

    /**
    empty string
     */
    final static String EMPTY = "";

    /**
    regex for int as a variable given to assign
     */
    final static Pattern INT_FORMAT = Pattern.compile("-?\\d+");

    /**
    regex for double as a variable given to assign
     */
    final static Pattern DOUBLE_FORMAT = Pattern.compile("-?\\d+.\\d*");

    /**
    regex for char as a variable given to assign
     */
    final static Pattern CHAR_FORMAT = Pattern.compile("\\'(.)\\'");

    /**
    regex for string as a variable given to assign
     */
    final static Pattern STRING_FORMAT = Pattern.compile("\\\"(.*)\\\"");

    /**
    regex for boolean as a variable given to assign
     */
    static final Pattern BOOLEAN_FORMAT = Pattern.compile("\\d+(.\\d*)?|true|false");

    /**
    regex for the equal part of the variable line
     */
    static final Pattern VARIABLE_INITIALIZATIONS_REGEX = Pattern.compile("\\s*=\\s*"); // might be a space zero
    // or more times


    /**
     * method to trim out a line according to matcher
     * @param matcher matcher to cut accordingly to
     * @param toAddToEnd index to add to find index to cut out
     * @param currentToTrim line to trim
     * @return string cutted
     */
    static String cutAndTrimToEnd(Matcher matcher, int toAddToEnd, String currentToTrim) {
        currentToTrim = currentToTrim.substring(matcher.end() + toAddToEnd);
        currentToTrim = currentToTrim.trim();
        return currentToTrim;
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
     * @throws UnsupportedCommand                   unknown command in sjava file
     * @throws InconvertibleTypeAssignment          type given does not match assignment
     * @throws ValueToFinalVariableException        value trying to assign to a final var
     * @throws VariableNameTaken                    variable name is taken already
     * @throws UnexpectedValueToVariable            no matching type given to variable value
     * @throws MissingValueInVariable               missing value in assign to variable
     * @throws ReturnMissingAtEndOfMethod           no return in end of method
     * @throws UnsupportedCommandInMethod           command format unsupported
     * @throws MissingParenthesisException          missing parent' in block
     * @throws SyntaxError                          syntax error in some line
     */
    void test() throws MissingMethodReturnType, IllegalReturnValue,
            MissingMethodName, MissingMethodParameters, UnexpectedValueToMethodSignature,
            MethodNameTaken, UnexpectedParameterInMethodSignature, MissingVariableName,
            UndeclaredVariableType, UnsupportedCommand, InconvertibleTypeAssignment, ValueToFinalVariableException,
            VariableNameTaken, UnexpectedValueToVariable, MissingValueInVariable, ReturnMissingAtEndOfMethod,
            UnsupportedCommandInMethod, MissingParenthesisException, SyntaxError;
}
