插入数据：insert into table_name (one,two,three) values (one,two,three)
删除数据：delete from table_name
修改数据：update table_name set one=one,two=two
查询数据：select * from table_name

#### 存储过程

创建存储过程：
delimiter //
create procedure procedure_name
begin
select * from table_name;
end //
delimiter ;
