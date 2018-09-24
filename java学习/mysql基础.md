https://www.cnblogs.com/imyalost/p/7223495.html?utm_source=itdadao&utm_medium=referral

插入数据：insert into table_name (one,two,three) values (one,two,three)

删除数据：delete from table_name

修改数据：update table_name set one=one,two=two

查询数据：select * from table_name

#### 存储过程
https://www.yiibai.com/mysql/stored-procedure.html

创建存储过程：
```
delimiter //
create procedure procedure_name
begin
select * from table_name;
end //
delimiter ;
```

声明、赋值变量：
声明：declare one int default 0;
<br />
赋值：set one=10;
<br />
赋值（select into）：
<br />
SELECT col_name[,...] INTO var_name[,...] table_expr 
<br />
col_name：要从数据库中查询的列字段名；
<br />
var_name：变量名，列字段名按照在列清单和变量清单中的位置对应，将查询得到的值赋给对应位置的变量；
<br />
table_expr：SELECT语句中的其余部分，包括可选的FROM子句和WHERE子句。
<br />
需要注意的是，在使用SELECT …INTO语句时，变量名不能和数据表中的字段名不能相同，否则会出错
<br />

存储过程入参（in,out）：
in比较容易理解
out如何接受输出的参数，在java中使用CallableStatement时，示例代码：
```
CallableStatement cstmt = conn.prepareCall("{call add_pro(?,?,?)}")
cstmt.setInt(1,4);
cstmt.setInt(2,5);
cstmt.registerOutParameter(3,Types.INTEGER);
cstmt.execute();
System.out.println("执行结果是:"+cstmt.getInt(3));
```

判断语句（if）:
```
IF expression THEN
   statements;
ELSEIF elseif-expression THEN
   elseif-statements;
...
ELSE
   else-statements;
END IF;
```

case when语句(switch case)：
```
 CASE customerCountry
 WHEN  'USA' THEN
    SET p_shiping = '2-day Shipping';
 WHEN 'Canada' THEN
    SET p_shiping = '3-day Shipping';
 ELSE
    SET p_shiping = '5-day Shipping';
 END CASE;
```

循环：
```
WHILE expression DO
   statements
END WHILE
```

mysql的事务隔离界别：http://www.cnblogs.com/zhoujinyi/p/3437475.html

http://ifeve.com/db_problem/

mysql默认的事务隔离级别：可重复读

四种隔离级别：

·未提交读(Read Uncommitted)：允许脏读，也就是可能读取到其他会话中未提交事务修改的数据

·提交读(Read Committed)：只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)

·可重复读(Repeated Read)：可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读

·串行读(Serializable)：完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞

脏读：脏读就是指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据。

不可重复读：是指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。

幻读：第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。那么，以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样。

#### 索引

主键索引

普通索引

唯一索引

组合索引





