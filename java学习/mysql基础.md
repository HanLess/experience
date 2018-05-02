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


