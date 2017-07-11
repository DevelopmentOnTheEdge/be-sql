/* Generated By:JJTree: Do not edit this line. AstUpdate.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstUpdate extends SimpleNode
{

    public AstUpdate(AstTableName tableName, AstUpdateSetList astUpdateSetList)
    {
        this( SqlParserTreeConstants.JJTUPDATE );
        addChild(tableName);
        addChild(astUpdateSetList);
    }

    public AstUpdate(int id)
    {
        super(id);
        this.nodePrefix = "UPDATE";
    }

    public AstUpdate(SqlParser p, int id) {
        super(p, id);
    }

}
/* JavaCC - OriginalChecksum=a166eea221758a97dda82854eb2103cd (do not edit this line) */
