package com.developmentontheedge.sql;

import com.developmentontheedge.sql.format.dbms.Context;
import com.developmentontheedge.sql.format.dbms.Dbms;
import com.developmentontheedge.sql.format.dbms.Formatter;
import com.developmentontheedge.sql.format.LimitsApplier;
import com.developmentontheedge.sql.model.AstStart;
import com.developmentontheedge.sql.model.DefaultParserContext;
import com.developmentontheedge.sql.model.SqlQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LimitsApplierTest
{
    @Test
    public void testLimitsApplier()
    {
        AstStart start = SqlQuery.parse("SELECT * FROM products p ORDER BY buyprice DESC");
        new LimitsApplier(10, 20).transformQuery(start.getQuery());
        assertEquals("SELECT * FROM products p ORDER BY buyprice DESC LIMIT 10, 20",
                new Formatter().format(start, new Context(Dbms.MYSQL)));
        assertEquals("SELECT * FROM products p ORDER BY buyprice DESC LIMIT 20 OFFSET 10",
                new Formatter().format(start, new Context(Dbms.POSTGRESQL)));
        assertEquals(
                "SELECT * FROM (SELECT p.*, ROW_NUMBER() OVER( ORDER BY buyprice DESC) AS rn FROM products p) AS tmp WHERE tmp.rn BETWEEN 10 AND 30",
                new Formatter().format(start, new Context(Dbms.DB2)));
        assertEquals(
                "SELECT * FROM (SELECT p.*, ROW_NUMBER() OVER( ORDER BY buyprice DESC) AS rn FROM products p) AS tmp WHERE tmp.rn BETWEEN 10 AND 30",
                new Formatter().format(start, new Context(Dbms.SQLSERVER)));
        assertEquals(
                "SELECT * FROM (SELECT tmp.*, ROWNUM rn FROM (SELECT * FROM products p ORDER BY buyprice DESC) tmp WHERE ROWNUM <= 30) WHERE ROWNUM > 10",
                new Formatter().format(start, new Context(Dbms.ORACLE)));

        start = SqlQuery.parse("SELECT name, address FROM products ORDER BY buyprice DESC");
        new LimitsApplier(10, 20).transformQuery(start.getQuery());
        assertEquals("SELECT name, address FROM products ORDER BY buyprice DESC LIMIT 10, 20",
                new Formatter().format(start, new Context(Dbms.MYSQL)));
        assertEquals("SELECT name, address FROM products ORDER BY buyprice DESC LIMIT 20 OFFSET 10",
                new Formatter().format(start, new Context(Dbms.POSTGRESQL)));
        assertEquals(
                "SELECT name, address FROM (SELECT name, address, ROW_NUMBER() OVER( ORDER BY buyprice DESC) AS rn FROM products) AS tmp WHERE tmp.rn BETWEEN 10 AND 30",
                new Formatter().format(start, new Context(Dbms.DB2)));
        assertEquals(
                "SELECT name, address FROM (SELECT name, address, ROW_NUMBER() OVER( ORDER BY buyprice DESC) AS rn FROM products) AS tmp WHERE tmp.rn BETWEEN 10 AND 30",
                new Formatter().format(start, new Context(Dbms.SQLSERVER)));
        assertEquals(
                "SELECT name, address FROM (SELECT tmp.*, ROWNUM rn FROM (SELECT name, address FROM products ORDER BY buyprice DESC) tmp WHERE ROWNUM <= 30) WHERE ROWNUM > 10",
                new Formatter().format(start, new Context(Dbms.ORACLE)));
    }
}
