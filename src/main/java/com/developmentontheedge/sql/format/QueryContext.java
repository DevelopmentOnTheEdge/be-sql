package com.developmentontheedge.sql.format;

import com.developmentontheedge.sql.model.AstBeSqlSubQuery;

import java.util.List;
import java.util.Map;

/**
 * Query execution context
 * Must be immutable object
 */
public interface QueryContext
{
    /**
     * @param name parameter name
     * @return parameter value by name or null if parameter absent
     */
    List<String> getListParameter(String name);

    String getParameter(String name);

    Map<String, List<Object>> getParameters();

    Map<String, String> asMap();

    /**
     * @param name session variable name
     * @return session variable value or null if such variable does not exist
     */
    Object getSessionVariable(String name);

    String resolveQuery(String entity, String name);

    /**
     * @return name of the current user
     */
    String getUserName();

    Map<String, AstBeSqlSubQuery> getSubQueries();

    List<String> roles();

    String getDictionaryValue(String tagName, String name, Map<String, String> parameters);
}
