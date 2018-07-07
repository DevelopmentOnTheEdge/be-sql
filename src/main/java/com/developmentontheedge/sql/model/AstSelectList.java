/* Generated By:JJTree: Do not edit this line. AstSelectList.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstSelectList extends SimpleNode
{
    public AstSelectList(int id)
    {
        super(id);
    }

    public AstSelectList()
    {
        this(SqlParserTreeConstants.JJTSELECTLIST);
    }

    public AstSelectList(SimpleNode... childs)
    {
        this(SqlParserTreeConstants.JJTSELECTLIST);
        for (SimpleNode child : childs)
            addChild(child);
    }

    public boolean isAllColumns()
    {
        return jjtGetNumChildren() == 0;
    }

    @Override
    public String getNodeContent()
    {
        return isAllColumns() ? "*" : null;
    }
}
/* JavaCC - OriginalChecksum=212875c1902cae7043268f0c3da8dd9e (do not edit this line) */
