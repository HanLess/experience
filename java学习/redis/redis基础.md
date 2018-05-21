redis是key ／ value形式的数据库

redis的数据类型：

1 字符串 String
2 字符串列表 list
3 有序字符串集合 sorted set
4 哈希 hash
5 字符串集合 set

常用的是1 和 4

String：

在内存中以二进制存储，存储长度不能超过512M

字符串的赋值：set (key) (value)

取值 : get (key) \ getset (key) (value)

// 在redis中加减操作，需要先将字符串转成数字类型，如果不能转，则报错
增 : 递增 incr (key) \ 增加number incrby (key) number

减 : 递减 decr (key) \ 减少number decrby (key) number

拼接：append (key) str 

删除 : del (key)


Hash：

String的key和String的value的map容器（键值对）

赋值：hset (hashName) (key) (value) / hmset (hashName) (key1) (value1) (key) (value2) ...

取值：hget (hashName) (key) / hmget (hashName) (key1) (key2) ... / hgetall (hashName)

删除：hdel (hashName) (key1) (key2) ... / del (hashName)

// 规格与字符串增加数字一样
增加数字：hincrby (hashName) (key) number

自学命令
