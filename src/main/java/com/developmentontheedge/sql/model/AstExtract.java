/* Generated By:JJTree: Do not edit this line. AstExtract.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

import java.util.function.Predicate;

public class AstExtract extends SimpleNode
{
    public AstExtract(int id)
    {
        super( id );
        this.nodePrefix = "EXTRACT(";
        this.nodeSuffix = ")";
    }

    public AstExtract(String dateField, SimpleNode child)
    {
        this( SqlParserTreeConstants.JJTEXTRACT );
        setDateField( dateField );
        addChild( child );
    }

    private String dateField;
    public void setDateField(String dateField)
    {
        this.dateField = dateField;
        this.nodePrefix = "EXTRACT(" + dateField + " FROM";
    }

    public String getDateField()
    {
        return dateField;
    }
    
    public static Predicate<SimpleNode> isExtract(String dateField)
    {
        return node -> node instanceof AstExtract && dateField.equals( ( (AstExtract)node ).getDateField() );
    }
}

/* JavaCC - OriginalChecksum=475761763c0919a8df5147e249d9c5f1 (do not edit this line) */
