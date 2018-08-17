mysql查询慢： http://blog.jobbole.com/107256/
结论：嵌套子查询非常耗时，建议逻辑在java中处理，避免嵌套查询

索引：理论上，sql中包含where等筛选，查询，排序条件，则涉及的列需要设置索引，但滥用索引会有两个缺点：占用存储空间大，insert慢。个人理解可以通过数据库读写分离解决

慢查询：http://www.cnblogs.com/kerrycode/p/5593204.html
