/* Generated By:JJTree: Do not edit this line. AstJoinSpecification.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

public class AstJoinSpecification extends SimpleNode
{
    public AstJoinSpecification(int id)
    {
        super( id );
        this.nodePrefix = "ON";
    }
    
    public AstJoinSpecification(SimpleNode child)
    {
        this( SqlParserTreeConstants.JJTJOINSPECIFICATION );
        addChild(child);
    }
}
/* JavaCC - OriginalChecksum=17a880170f458943f7a3ed8e71d61926 (do not edit this line) */
