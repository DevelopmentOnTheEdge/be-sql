/* Generated By:JJTree: Do not edit this line. AstQuery.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstQuery extends SimpleNode
{
    public AstQuery(int id)
    {
        super(id);
    }

    public AstQuery(AstSelect select)
    {
        this(SqlParserTreeConstants.JJTQUERY);
        addChild(select);
    }

    public AstSelect getSelect()
    {
        return children().select(AstSelect.class).findFirst().orElse(null);
    }

    @Override
    public AstQuery clone()
    {
        return (AstQuery) super.clone();
    }
}
/* JavaCC - OriginalChecksum=5a6b9311007d985e86eefc0f42f7e7d3 (do not edit this line) */
