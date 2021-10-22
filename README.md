# BE-SQL

[![Build Status](https://travis-ci.com/DevelopmentOnTheEdge/be-sql.svg?branch=master)](https://travis-ci.com/DevelopmentOnTheEdge/be-sql) [![Coverage Status](https://coveralls.io/repos/github/DevelopmentOnTheEdge/be-sql/badge.svg?branch=master)](https://coveralls.io/github/DevelopmentOnTheEdge/be-sql?branch=master)

Parse SQL and builds its AST.
Then DBMS specific SQL select statement can be generated.

BE-SQL — диалект SQL, который поддерживается BeanExplorer SQL Parser. 
Поддерживаются только запросы CRUD. 
Больше всего он похож на PostgreSQL, но некоторые конструкции из других диалектов тоже поддерживаются. 
Он также должен понимать различные BE-специфичные теги вроде &lt;if&gt;, &lt;unless&gt; и т. д. 

### Wiki
http://wiki.dote.ru/index.php/BE-SQL

