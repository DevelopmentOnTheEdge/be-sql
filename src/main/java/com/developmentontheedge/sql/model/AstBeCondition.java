/* Generated By:JJTree: Do not edit this line. AstIf.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

import one.util.streamex.StreamEx;

import java.util.Set;


public class AstBeCondition extends AstBeNode
{
    // TODO: support "session"
    private static final Set<String> ALLOWED_PARAMETERS = StreamEx.of( "parameter", "value", "expr", "columnExists", "session", "sessionExpr" ).toSet();

    public AstBeCondition(int id)
    {
        super( id );
        this.allowedParameters = ALLOWED_PARAMETERS;
    }

    public String getKey()
    {
        return getParameter( "parameter" );
    }

    public String getValue()
    {
        return getParameter( "value" );
    }

    public String getExpression()
    {
        return getParameter( "expr" );
    }
    
    public String getSession()
    {
        return getParameter( "session" );
    }
}
/* JavaCC - OriginalChecksum=574273ae5438a95cad4ff1695ce37939 (do not edit this line) */
