/* Generated By:JJTree: Do not edit this line. AstIdentifierConstant.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=true,NODE_PREFIX=Ast,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.developmentontheedge.sql.model;

import java.util.Objects;


public class AstIdentifierConstant extends SimpleNode
{
    public static enum QuoteSymbol
    {
        NONE, DOUBLE_QUOTE, BACKTICK
    }

    public AstIdentifierConstant(int id)
    {
        super(id);
    }

    public AstIdentifierConstant(String value)
    {
        this(SqlParserTreeConstants.JJTIDENTIFIERCONSTANT);
        setValue(value);
    }

    public AstIdentifierConstant(String value, boolean quote)
    {
        this(SqlParserTreeConstants.JJTIDENTIFIERCONSTANT);
        setValue(value);
        setQuoteSymbol(quote ? QuoteSymbol.DOUBLE_QUOTE : QuoteSymbol.NONE);
    }

    private String value;
    private QuoteSymbol symbol = QuoteSymbol.NONE;

    public String getValue()
    {
        return value;
    }


    public QuoteSymbol getQuoteSymbol()
    {
        return symbol;
    }

    public void setQuoteSymbol(QuoteSymbol symbol)
    {
        this.symbol = Objects.requireNonNull(symbol);
    }

    public void setValue(String value)
    {
        if (value.startsWith("\"") && value.endsWith("\""))
        {
            this.value = value.substring(1, value.length() - 1);
            this.symbol = QuoteSymbol.DOUBLE_QUOTE;
        }
        else if (value.startsWith("`") && value.endsWith("`"))
        {
            this.value = value.substring(1, value.length() - 1);
            this.symbol = QuoteSymbol.BACKTICK;
        }
        else
        {
            this.value = value;
            this.symbol = QuoteSymbol.NONE;
        }
    }

    @Override
    public String getNodeContent()
    {
        String qs;
        switch (symbol)
        {
            case BACKTICK:
                qs = "`";
                break;
            case DOUBLE_QUOTE:
                qs = "\"";
                break;
            case NONE:
                qs = "";
                break;
            default:
                throw new IllegalArgumentException();
        }
        return qs + value + qs;
    }

    @Override
    public String toString()
    {
        return super.toString() + ": " + getNodeContent();
    }
}
/* JavaCC - OriginalChecksum=e7bda9ba6e8f37120bae64bff0edb499 (do not edit this line) */
