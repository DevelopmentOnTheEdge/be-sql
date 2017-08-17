/* Generated By:JJTree: Do not edit this line. AstInValueList.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

import static com.google.common.base.Preconditions.checkArgument;

public class AstInValueList extends SimpleNode
{
    public static AstInValueList of(int count)
    {
        checkArgument(count > 0);

        AstInValueList astInValueList = new AstInValueList(SqlParserTreeConstants.JJTINVALUELIST);
        for (int i=0 ; i<count; i++)
        {
            astInValueList.addChild(AstReplacementParameter.get());
        }
        return astInValueList;
    }

    public AstInValueList(int id)
    {
        super( id );
        this.childrenDelimiter = ",";
        this.nodePrefix = "(";
        this.nodeSuffix = ")";
    }
}
/* JavaCC - OriginalChecksum=9e5d8b9eadad3808bdda702871fbaa75 (do not edit this line) */
