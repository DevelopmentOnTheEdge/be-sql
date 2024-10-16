# BE-SQL

[![Tests using Java 8](https://github.com/DevelopmentOnTheEdge/be-sql/actions/workflows/run_tests_on_push_java_8.yaml/badge.svg)](https://github.com/DevelopmentOnTheEdge/be-sql/actions/workflows/run_tests_on_push_java_8.yaml) [![Coverage Status](https://coveralls.io/repos/github/DevelopmentOnTheEdge/be-sql/badge.svg?branch=master)](https://coveralls.io/github/DevelopmentOnTheEdge/be-sql?branch=master)

Parse SQL and builds its AST.
Then DBMS specific SQL select statement can be generated.

BE-SQL — диалект SQL, который поддерживается BeanExplorer SQL Parser. 
Поддерживаются только запросы CRUD. 
Больше всего он похож на PostgreSQL, но некоторые конструкции из других диалектов тоже поддерживаются. 
Он также должен понимать различные BE-специфичные теги вроде &lt;if&gt;, &lt;unless&gt; и т. д. 

### Wiki
http://wiki.dote.ru/index.php/BE-SQL


## Maven

```xml
<dependency>
    <groupId>com.developmentontheedge</groupId>
    <artifactId>be-sql</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Gradle

```groovy
implementation group: 'com.developmentontheedge', name: 'be-sql', version: '0.1.0'
```