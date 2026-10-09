package com.developmentontheedge.sql.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PgVectorTest
{
    @Test
    public void distanceOperatorWithReplacementParameter()
    {
        assertEquals("SELECT id FROM chunks ORDER BY embedding <=> CAST(? AS vector) LIMIT 5",
                SqlQuery.parse("SELECT id FROM chunks ORDER BY embedding <=> ?::vector LIMIT 5").format());
    }

    @Test
    public void distanceOperators()
    {
        String query = "SELECT a <-> b, a <#> b, a <=> b, a <+> b, c <~> d, c <%> d FROM t";
        assertEquals(query, SqlQuery.parse(query).format());
    }

    @Test
    public void castToSizedVectorTypes()
    {
        String query = "SELECT CAST(x AS vector(768)), CAST(y AS HALFVEC(768)), CAST(z AS varchar(10)) FROM t";
        assertEquals(query, SqlQuery.parse(query).format());
        assertEquals("SELECT CAST(x AS vector(3)) FROM t", SqlQuery.parse("SELECT x::vector(3) FROM t").format());
    }

    @Test(expected = IllegalArgumentException.class)
    public void castSizeIsNotAllowedForText()
    {
        new AstCast(new AstIdentifierConstant("x"), "TEXT", 10);
    }
}
