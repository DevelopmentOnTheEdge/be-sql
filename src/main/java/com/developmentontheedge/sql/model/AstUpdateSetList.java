/* Generated By:JJTree: Do not edit this line. AstUpdateSetList.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstUpdateSetList extends SimpleNode
{
    public AstUpdateSetList(SimpleNode... childs)
    {
        this( SqlParserTreeConstants.JJTUPDATESETLIST );
        for( SimpleNode child : childs )
        {
            addChild(child);
        }
    }

    public AstUpdateSetList(int id) {
        super(id);
        this.nodePrefix = "SET";
        this.childrenDelimiter = ",";
    }

    public AstUpdateSetList(SqlParser p, int id) {
        super(p, id);
    }

}
/* JavaCC - OriginalChecksum=e26d075436197d894b98048e7d0550ae (do not edit this line) */
