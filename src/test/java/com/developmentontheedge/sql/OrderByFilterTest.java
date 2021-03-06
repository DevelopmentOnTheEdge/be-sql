package com.developmentontheedge.sql;

import com.developmentontheedge.sql.format.dbms.Context;
import com.developmentontheedge.sql.format.dbms.Dbms;
import com.developmentontheedge.sql.format.dbms.Formatter;
import com.developmentontheedge.sql.format.OrderByFilter;
import com.developmentontheedge.sql.model.AstStart;
import com.developmentontheedge.sql.model.DefaultParserContext;
import com.developmentontheedge.sql.model.SqlQuery;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OrderByFilterTest
{
    @Test
    public void testOrderByFilter()
    {
        AstStart start = SqlQuery.parse("SELECT t.a, t.b, t.c AS foo FROM myTable t WHERE t.b > 2");
        Map<String, String> columns = new HashMap<String, String>();
        columns.put("t.a", "ASC");
        columns.put("foo", "DESC");
        new OrderByFilter().apply(start, columns);
        assertEquals("SELECT t.a, t.b, t.c AS foo FROM myTable t WHERE t.b > 2 ORDER BY 1 ASC, 3 DESC",
                new Formatter().format(start, new Context(Dbms.MYSQL)));
    }

    @Test
    public void testOrderByFilterUnion()
    {
        AstStart start = SqlQuery.parse("SELECT name FROM bbc WHERE name LIKE 'Z%' UNION SELECT name FROM actor WHERE name LIKE 'Z%'");
        Map<String, String> columns = Collections.singletonMap("name", "DESC");
        new OrderByFilter().apply(start, columns);
        assertEquals("SELECT * FROM (SELECT name FROM bbc WHERE name LIKE 'Z%' UNION SELECT name FROM actor WHERE name LIKE 'Z%') "
                + "tmp ORDER BY 1 DESC", new Formatter().format(start, new Context(Dbms.MYSQL)));
    }
}
