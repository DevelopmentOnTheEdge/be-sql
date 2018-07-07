/* Generated By:JJTree: Do not edit this line. AstBeSql.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

import java.util.Set;

public class AstBeSql extends AstBeNode
{
    private static final Set<String> ALLOWED_PARAMETERS = AstBeSqlSubQuery.ALLOWED_PARAMETERS;

    public AstBeSql(int id)
    {
        super(id);
        allowedParameters = ALLOWED_PARAMETERS;
        this.tagName = "sql";
    }

    public AstQuery getQuery()
    {
        return this.children().select(AstQuery.class).findFirst().orElse(null);
    }

    public Integer getLimit()
    {
        String limit = getParameter("limit");
        return limit == null ? null : Integer.valueOf(limit);
    }

    public String getExec()
    {
        return getParameter("exec") == null ? "" : getParameter("exec");
    }

    public String getQueryName()
    {
        return getParameter("queryName");
    }

    public String getEntityName()
    {
        return getParameter("entity");
    }

    public String getBeautifier()
    {
        return getParameter("beautifier") == null ? "" : getParameter("beautifier");
    }

    public String getDistinct()
    {
        return getParameter("distinct") == null ? "" : getParameter("distinct");
    }
}
/* JavaCC - OriginalChecksum=45e233ad5e92f21b98f767335a562cfe (do not edit this line) */
