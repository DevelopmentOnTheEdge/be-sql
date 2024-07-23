package com.developmentontheedge.sql.model;

import com.developmentontheedge.sql.format.dbms.DateFormat;
import com.developmentontheedge.sql.format.dbms.Dbms;
import one.util.streamex.StreamEx;

import java.util.HashMap;
import java.util.Locale;

import static com.developmentontheedge.sql.format.dbms.Dbms.DB2;
import static com.developmentontheedge.sql.format.dbms.Dbms.MYSQL;
import static com.developmentontheedge.sql.format.dbms.Dbms.ORACLE;
import static com.developmentontheedge.sql.format.dbms.Dbms.POSTGRESQL;
import static com.developmentontheedge.sql.format.dbms.Dbms.SQLSERVER;
import static com.developmentontheedge.sql.model.Function.AGGREGATE_FUNCTION_PRIORITY;
import static com.developmentontheedge.sql.model.Function.FUNCTION_PRIORITY;

/**
 *
 */
public class DefaultParserContext implements ParserContext
{
    private DefaultParserContext()
    {
        declareStandardOperators(this);
        declareSqlFunctions(this);
    }

    public static DefaultParserContext getInstance()
    {
        return instance;
    }

    ParserContext parent = null;

    public ParserContext getParentContext()
    {
        return parent;
    }

    public void setParentContext(ParserContext parent)
    {
        this.parent = parent;
    }

    ///////////////////////////////////////////////////////////////////
    // Function issues
    //

    protected HashMap<String, Function> functionsMap = new HashMap<>();

    @Override
    public Function getFunction(String name)
    {
        name = name.toLowerCase(Locale.ENGLISH);

        Function function = functionsMap.get(name);
        if (function == null && parent != null)
            function = parent.getFunction(name);

        return function;
    }

    public HashMap<String, Function> getFunctionsMap()
    {
        return functionsMap;
    }

    @Override
    public void declareFunction(Function function, String... otherNames)
    {
        functionsMap.put(function.getName().toLowerCase(Locale.ENGLISH), function);
        for (String n : otherNames)
            functionsMap.put(n.toLowerCase(Locale.ENGLISH), function);
    }

    /*
     * Declares standard operators:
     *
     * @todo implement properly
     * @todo declare operator names as constants
     */
    public static final String OR = "||";
    public static final String OR_LIT = "OR";
    public static final String AND = "&&";
    public static final String AND_LIT = "AND";
    public static final String NOT = "!";
    public static final String NOT_LIT = "NOT";
    public static final String XOR = "XOR";

    public static final String GT = ">";
    public static final String LT = "<";
    public static final String GEQ = ">=";
    public static final String LEQ = "<=";
    public static final String EQ = "=";
    public static final String EQQ = "==";
    public static final String NEQ = "!=";
    public static final String LTGT = "<>";
    public static final String LIKE = "LIKE";
    public static final String NOT_LIKE = "NOT LIKE";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT IN";
    public static final String UPPER = "UPPER";
    public static final String LOWER = "LOWER";

    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String UMINUS = "u-";
    public static final String TIMES = "*";
    public static final String DIVIDE = "/";
    public static final String MOD = "%";

    public static final String BIT_AND = "&";
    public static final String BIT_OR = "|";

    public static final String REGEXP_MATCH = "~";

    public static final String OP_CONCAT = "||";

    public static final String GET_FIELD = "->";
    public static final String GET_FIELD_TXT = "->>";
    public static final String EXTRACT_PATH = "#>";
    public static final String EXTRACT_PATH_TXT = "#>>";

    public static final PredefinedFunction FUNC_OR = new PredefinedFunction(OR_LIT, Function.LOGICAL_PRIORITY, -1);
    public static final PredefinedFunction FUNC_AND = new PredefinedFunction(AND_LIT, Function.LOGICAL_PRIORITY, -1);
    public static final PredefinedFunction FUNC_NOT = new PredefinedFunction(NOT_LIT, Function.UNARY_PRIORITY, 1);
    public static final PredefinedFunction FUNC_XOR = new PredefinedFunction(XOR, Function.LOGICAL_PRIORITY, -1);

    public static final PredefinedFunction FUNC_EQ = new PredefinedFunction(EQ, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_GT = new PredefinedFunction(GT, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_LT = new PredefinedFunction(LT, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_GEQ = new PredefinedFunction(GEQ, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_LEQ = new PredefinedFunction(LEQ, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_LTGT = new PredefinedFunction(LTGT, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_LIKE = new PredefinedFunction(LIKE, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_NOT_LIKE = new PredefinedFunction(NOT_LIKE, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_IN = new PredefinedFunction(IN, Function.RELATIONAL_PRIORITY, 2);
    public static final PredefinedFunction FUNC_NOT_IN = new PredefinedFunction(NOT_IN, Function.RELATIONAL_PRIORITY, 2);

    public static final PredefinedFunction FUNC_PLUS = new PredefinedFunction(PLUS, Function.PLUS_PRIORITY, -1);
    public static final PredefinedFunction FUNC_MINUS = new PredefinedFunction(MINUS, Function.PLUS_PRIORITY, 2);
    public static final PredefinedFunction FUNC_UMINUS = new PredefinedFunction(UMINUS, Function.UNARY_PRIORITY, 1);
    public static final PredefinedFunction FUNC_TIMES = new PredefinedFunction(TIMES, Function.TIMES_PRIORITY, -1);
    public static final PredefinedFunction FUNC_DIVIDE = new PredefinedFunction(DIVIDE, Function.TIMES_PRIORITY, 2);
    public static final PredefinedFunction FUNC_MOD = new PredefinedFunction(MOD, Function.TIMES_PRIORITY, 2);

    public static final PredefinedFunction FUNC_CONCAT = new PredefinedFunction(OP_CONCAT, Function.LOGICAL_PRIORITY, 2);

    public static final PredefinedFunction FUNC_UPPER = new PredefinedFunction(UPPER, FUNCTION_PRIORITY, 1);
    public static final PredefinedFunction FUNC_LOWER = new PredefinedFunction(LOWER, FUNCTION_PRIORITY, 1);

    private static final DefaultParserContext instance = new DefaultParserContext();

    public static void declareStandardOperators(ParserContext context)
    {
        // Logical operators
        context.declareFunction(FUNC_OR);
        context.declareFunction(FUNC_AND, AND);
        context.declareFunction(FUNC_NOT, NOT);
        context.declareFunction(FUNC_XOR);

        // Relational operators
        context.declareFunction(FUNC_GT);
        context.declareFunction(FUNC_LT);
        context.declareFunction(FUNC_EQ, EQQ);
        context.declareFunction(FUNC_GEQ);
        context.declareFunction(FUNC_LEQ);
        context.declareFunction(FUNC_LTGT, NEQ);
        context.declareFunction(FUNC_LIKE);
        context.declareFunction(FUNC_NOT_LIKE);
        context.declareFunction(FUNC_IN);
        context.declareFunction(FUNC_NOT_IN);
        context.declareFunction(new DbSpecificFunction(new PredefinedFunction(REGEXP_MATCH, Function.RELATIONAL_PRIORITY, 2), POSTGRESQL, MYSQL, ORACLE));

        // Arithmetic operators
        context.declareFunction(FUNC_PLUS);
        context.declareFunction(FUNC_MINUS);
        context.declareFunction(FUNC_DIVIDE);
        context.declareFunction(FUNC_TIMES);
        context.declareFunction(FUNC_UMINUS);
        context.declareFunction(FUNC_MOD);
        context.declareFunction(new PredefinedFunction(BIT_AND, Function.PLUS_PRIORITY, 2));
        context.declareFunction(new PredefinedFunction(BIT_OR, Function.PLUS_PRIORITY, 2));

        // JSON
        context.declareFunction(new DbSpecificFunction(new PredefinedFunction(GET_FIELD, Function.LOGICAL_PRIORITY, 2), POSTGRESQL));
        context.declareFunction(new DbSpecificFunction(new PredefinedFunction(GET_FIELD_TXT, Function.LOGICAL_PRIORITY, 2), POSTGRESQL));
        context.declareFunction(new DbSpecificFunction(new PredefinedFunction(EXTRACT_PATH, Function.LOGICAL_PRIORITY, 2), POSTGRESQL));
        context.declareFunction(new DbSpecificFunction(new PredefinedFunction(EXTRACT_PATH_TXT, Function.LOGICAL_PRIORITY, 2), POSTGRESQL));
    }

    public static void declareSqlFunctions(ParserContext context)
    {
        context.declareFunction(FUNC_CONCAT);

        function(context, "CONCAT");
        function(context, "LEAST");
        function(context, "GREATEST");
        function(context, "COALESCE", "IFNULL");
        function(context, "LENGTH", "LEN"); // TODO: check number of arguments

        context.declareFunction(new PredefinedFunction("SUM", AGGREGATE_FUNCTION_PRIORITY, 1));
        function(context, 1, "MAX");
        function(context, 1, "MIN");

        function(context, 2, 3, "SUBSTR", "SUBSTRING");
        function(context, 2, "RIGHT");
        function(context, 2, "LEFT");

        function(context, 1, UPPER);
        function(context, 1, LOWER);
        function(context, 1, "CHR", "CHAR");

        function(context, 1, 2, "TO_CHAR");

        function(context, 1, "TO_NUMBER");
        function(context, 1, "TO_KEY");
        function(context, 3, "REPLACE");

        function(context, 1, 2, "ROUND");
        function(context, 1, 2, "TRUNC", "TRUNCATE");

        function(context, 3, "LPAD");
        function(context, 1, "LTRIM");
        function(context, 1, "RTRIM");
        function(context, 1, "TRIM");

        function(context, 2, 3, "IF");
        function(context, 2, "NULLIF");

        function(context, 0, "NOW");
        function(context, 2, "DATE_FORMAT");
        function(context, 2, "DATE_TRUNC");

        StreamEx.of(DateFormat.values()).forEach(df -> function(context, df.name()));

        function(context, 1, 2, "TO_DATE");

        function(context, 2, "INSTR", "STRPOS");
        function(context, 2, "ADD_MONTHS");
        function(context, 2, "ADD_DAYS");
        function(context, 2, "ADD_MILLIS");
        function(context, 1, "LAST_DAY");
        function(context, 1, "GROUPING");

        function(context, 2, "YEARDIFF");
        function(context, 2, "SECONDDIFF");
        function(context, 2, "MINUTEDIFF");
        function(context, 2, "HOURDIFF");
        function(context, 2, "DAYDIFF");
        function(context, 2, "MONTHDIFF");
        function(context, 2, "AGE");
        function(context, 3, "TIMESTAMPDIFF");
        function(context, 1, "FLOOR");
        function(context, 2, "MOD");
        function(context, 1, "ABS");

        function(context, 0, "ROW_NUMBER");
        function(context, 0, "RANK");
        function(context, 1, "AVG");
        function(context, 3, -1, "DECODE");

        dbSpecificFunction(context, "PG_RELATION_SIZE", FUNCTION_PRIORITY, 1, POSTGRESQL);
        dbSpecificFunction(context, "PG_SIZE_PRETTY",   FUNCTION_PRIORITY, 1, POSTGRESQL);
        dbSpecificFunction(context, "REGEXP_INSTR",     FUNCTION_PRIORITY, 2, POSTGRESQL);
        dbSpecificFunction(context, "REGEXP_REPLACE",   FUNCTION_PRIORITY, 2, 4, DB2, POSTGRESQL, ORACLE);
        dbSpecificFunction(context, "REGEXP_LIKE",      FUNCTION_PRIORITY, 2, POSTGRESQL, MYSQL, ORACLE);
        dbSpecificFunction(context, "DENSE_RANK",       FUNCTION_PRIORITY, 0, DB2, POSTGRESQL, ORACLE, SQLSERVER);
        dbSpecificFunction(context, "STRING_AGG",       FUNCTION_PRIORITY, 1, 2, DB2, POSTGRESQL, ORACLE, MYSQL);
        dbSpecificFunction(context, "TRANSLATE",        FUNCTION_PRIORITY, 3, DB2, POSTGRESQL, ORACLE);
        dbSpecificFunction(context, "LEVENSHTEIN",      FUNCTION_PRIORITY, 2, POSTGRESQL, ORACLE);
        dbSpecificFunction(context, "REVERSE",          FUNCTION_PRIORITY, 1, MYSQL);
        dbSpecificFunction(context, "SUBSTRING_INDEX",  FUNCTION_PRIORITY, 3, MYSQL);
        dbSpecificFunction(context, "JSON_UNQUOTE",     FUNCTION_PRIORITY, 1, MYSQL);
        dbSpecificFunction(context, "JSON_EXTRACT",     FUNCTION_PRIORITY, 2, MYSQL);
    }

    private static void function(ParserContext context, String name, String... otherNames)
    {
        context.declareFunction(new PredefinedFunction(name, FUNCTION_PRIORITY, -1), otherNames);
    }

    private static void function(ParserContext context, int numParams, String name, String... otherNames)
    {
        context.declareFunction(new PredefinedFunction(name, FUNCTION_PRIORITY, numParams), otherNames);
    }

    private static void function(ParserContext context, int minParams, int maxParams, String name, String... otherNames)
    {
        context.declareFunction(new PredefinedFunction(name, FUNCTION_PRIORITY, minParams, maxParams), otherNames);
    }

    private static void dbSpecificFunction(ParserContext context, String name, int priority, int numParams, Dbms... dbms)
    {
        PredefinedFunction predefinedFunction = new PredefinedFunction(name, priority, numParams);
        context.declareFunction(new DbSpecificFunction(predefinedFunction, dbms));
    }

    private static void dbSpecificFunction(ParserContext context, String name, int priority, int minParams, int maxParams, Dbms... dbms)
    {
        PredefinedFunction predefinedFunction = new PredefinedFunction(name, priority, minParams, maxParams);
        context.declareFunction(new DbSpecificFunction(predefinedFunction, dbms));
    }

}
