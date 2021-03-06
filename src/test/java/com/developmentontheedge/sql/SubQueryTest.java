package com.developmentontheedge.sql;

import com.developmentontheedge.sql.format.BasicQueryContext;
import com.developmentontheedge.sql.format.BasicQueryContext.QueryResolver;
import com.developmentontheedge.sql.format.ContextApplier;
import com.developmentontheedge.sql.model.AstBeSqlSubQuery;
import com.developmentontheedge.sql.model.AstStart;
import com.developmentontheedge.sql.model.SqlQuery;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class SubQueryTest
{
    @Test
    public void testSubQueryResolver()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\" queryName=\"test\"></sql>' FROM table");
        QueryResolver resolver = (entity, query) -> {
            assertNull(entity);
            assertEquals("test", query);
            return "SELECT * FROM subTable WHERE tableID=<var:ID/>";
        };
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().queryResolver(resolver).build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT * FROM subTable WHERE tableID = 5 LIMIT 2", subQuery.getQuery().format());
    }

    @Test
    public void testSubQueryResolverBeVar()
    {
        AstStart start = SqlQuery.parse("SELECT 'select * from test' AS \"___code\", '<sql><var:___code safestr=\"no\"/></sql>' FROM table");

        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().build());
        contextApplier.applyContext(start);

        assertEquals("SELECT 'select * from test' AS \"___code\", '<sql> SubQuery# 1</sql>' FROM table", start.getQuery().toString());
    }

    @Test
    public void testSubQueryResultParse()
    {
        String sql = "SELECT\n" +
                "      t.name AS \"___Name\",\n" +
                "      '<sql>SubQuery#1</sql>' AS \"testtUserValues\"\n" +
                "    FROM\n" +
                "      testtable t ORDER BY 2 LIMIT 2147483647";
        SqlQuery.parse(sql);
    }

    @Test
    public void testSubQueryResolverCheckExpression()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\" queryName=\"test\"></sql>' FROM table");
        QueryResolver resolver = (entity, query) -> {
            assertNull(entity);
            assertEquals("test", query);
            return "SELECT * FROM subTable WHERE tableID=<var:ID/>" +
                    "<if parameter=\"idList\">\n" +
                    "   AND us.ID IN <parameter:idList multiple=\"true\" refColumn=\"utilitySuppliers.ID\" />\n" +
                    "</if>";
        };
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().queryResolver(resolver).build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT * FROM subTable WHERE tableID = 5 LIMIT 2", subQuery.getQuery().format());
    }

    @Test
    public void testSubQueryResolverExecInclude()
    {
        AstStart start = SqlQuery.parse("SELECT * FROM table t\n" +
                "            LEFT JOIN (<sql exec=\"include\" entity=\"meters\" queryName=\"*** Selection view ***\"></sql>) m\n" +
                "                ON m.ID = t.meterID");
        QueryResolver resolver = (entity, query) -> {
            assertEquals("meters", entity);
            assertEquals("*** Selection view ***", query);
            return "SELECT m.ID FROM public.meters m";
        };
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().queryResolver(resolver).build());
        contextApplier.applyContext(start);
        assertEquals("SELECT * FROM table t\n" +
                "            LEFT JOIN (SELECT m.ID FROM public.meters m) m\n" +
                "                ON m.ID = t.meterID", start.format());
    }

    @Test
    public void testApplyWithVarsExecDelayedAddFilter()
    {
        AstStart start = SqlQuery.parse(
                "SELECT '<sql exec=\"delayed\" filterKey=\"us.ID\" filterValProperty=\"___usID\" limit=\"1\" " +
                        "entity=\"utilitySuppliers\" queryName=\"*** Selection view ***\" outColumns=\"Name\"></sql>' AS \"Услуга\",\n" +
                        "ID AS \"___usID\" FROM table");
        QueryResolver resolver = (entity, query) -> {
            assertEquals("utilitySuppliers", entity);
            assertEquals("*** Selection view ***", query);
            return "SELECT us.ID AS \"CODE\",us.utilityType AS \"Name\" FROM utilitySuppliers us";
        };
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().queryResolver(resolver).build());
        contextApplier.applyContext(start);

        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("___usID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);

        assertEquals("SELECT '" + key + "' AS \"Услуга\",\n" +
                "ID AS \"___usID\" FROM table", start.format());

        assertEquals("SELECT us.utilityType AS \"Name\" " +
                "FROM utilitySuppliers us WHERE us.ID = 5 LIMIT 1", subQuery.getQuery().format());
    }

    @Test
    public void testApplyWithVars()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\">SELECT COUNT(*) FROM subTable WHERE tableID=<var:ID/></sql> entries' FROM table");
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT COUNT(*) FROM subTable WHERE tableID = 5 LIMIT 2", subQuery.getQuery().format());
        assertEquals("SELECT ID, '" + key + " entries' FROM table", start.format());
    }

    @Test
    public void testSeveralSubQueries()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\">SELECT * FROM subTable WHERE tableID=<var:ID/></sql>' FROM table");
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT * FROM subTable WHERE tableID = 5 LIMIT 2", subQuery.getQuery().format());
    }

    @Test
    public void testApplyWithPrepareParams()
    {
        AstStart start = SqlQuery.parse("SELECT ID, " +
                "'<sql>SELECT name FROM subTable WHERE id = ?</sql>' AS \"val\" FROM table");
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT name FROM subTable WHERE id = ?", subQuery.getQuery().format());
    }

    @Test
    public void testVarsInFieldReference()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\">SELECT field.<var:reference/> FROM table.<var:name/></sql>' FROM table");
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = new HashMap<>();
        vars.put("reference", "ref");
        vars.put("name", "name");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT field.ref FROM table.name LIMIT 2", subQuery.getQuery().format());
    }

    @Test
    public void testOutColumns()
    {
        AstStart start = SqlQuery.parse("SELECT ID, '<sql limit=\"2\" queryName=\"test\"" +
                "outColumns=\"Foo\"></sql>' FROM table");
        QueryResolver resolver = (entity, query) -> {
            assertNull(entity);
            assertEquals("test", query);
            return "SELECT name AS \"Name\", foo AS \"Foo\", bar AS \"Bar\" FROM subTable WHERE tableID=<var:ID/>";
        };
        ContextApplier contextApplier = new ContextApplier(new BasicQueryContext.Builder().queryResolver(resolver).build());
        contextApplier.applyContext(start);
        String key = contextApplier.subQueryKeys().findFirst().get();
        Map<String, String> vars = Collections.singletonMap("ID", "5");
        AstBeSqlSubQuery subQuery = contextApplier.getSubQuery(key, vars::get);
        assertEquals("SELECT foo AS \"Foo\" FROM subTable WHERE tableID = 5 LIMIT 2", subQuery.getQuery().format());
    }
}
