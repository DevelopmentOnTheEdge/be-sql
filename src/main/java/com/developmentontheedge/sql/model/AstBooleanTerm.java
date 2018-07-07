/* Generated By:JJTree: Do not edit this line. AstBooleanTerm.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstBooleanTerm extends AstFunNode implements Squasheable
{
    public AstBooleanTerm(SimpleNode a, SimpleNode b)
    {
        super(SqlParserTreeConstants.JJTBOOLEANTERM);
        addChild(a);
        addChild(b);
        setFunction(DefaultParserContext.FUNC_AND);
    }

    public AstBooleanTerm(int id)
    {
        super(id);
        setFunction(DefaultParserContext.FUNC_AND);
    }

    @Override
    public String getChildrenDelimiter(SimpleNode prev, SimpleNode next)
    {
        return next instanceof AstBeConditionChain ? null : childrenDelimiter;
    }

    @Override
    public Token getSpecialPrefix()
    {
        return jjtGetParent() instanceof AstBeNode ? null : super.getSpecialPrefix();
    }

    @Override
    public String getNodePrefix()
    {
        return jjtGetParent() instanceof AstBeNode ? childrenDelimiter : super.getNodePrefix();
    }
}
/* JavaCC - OriginalChecksum=e5d03fb4b83289cd9224b65015b80b02 (do not edit this line) */
