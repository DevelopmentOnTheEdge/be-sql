/* Generated By:JJTree: Do not edit this line. AstBooleanExpression.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstBooleanExpression extends AstFunNode implements Squasheable
{
    public AstBooleanExpression(int id)
    {
        super( id );
        setFunction( DefaultParserContext.FUNC_OR );
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
/* JavaCC - OriginalChecksum=ad1c09f0f5b3870f7823b578066ca1b2 (do not edit this line) */
