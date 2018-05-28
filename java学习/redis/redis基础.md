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

list : 

链表，与数据结构中的链表一样，可以在表头，表尾插入元素，时间复杂度是 1，如果插入的链表不存在，会先创建；如果将表中的所有元素删除，则表也会被删除（自动创建，自动删除）

两端添加：lpush (listName) (value1,value2,value3 ...) / rpush / lpushx (listName) (value...) 当 listName 存在时才插入       （lpush 左侧添加 rpush 右侧添加）

查看：lrange (listName) (start,end)        （如果start,end是负数，表示从‘倒数’第几个开始，或‘倒数’第几个结束）

两端弹出：lpop (listName) 左端弹出 ／ rpop (listName) 右端弹出

获取表中元素数量：llen (listName)

扩展命令

Set : 

无序集合，与java中的set一样，不允许重复

添加：sadd (setName) (value...)

删除：srem (setName) (value...)

获得元素：smembers (setName) / 判断元素是否存在 sismember (setName) (value)

集合差集：sdiff (setName1) (setName2)

交集：sinter (setName1) (setName2)

并集：sunion (setName1) (setName2)

使用场景：存储唯一性的数据、需要操作两个集合的交并集等

sortedSet :

有序集合，与java中的sortedSet一样

在redis中，是根据 score 来排序，所以在添加等操作中，需要指定元素的分数，如添加：zadd (sortName) (score) (value)


redis特性：

1 多数据库：redis可以创建16个数据库，通过下标可以切换（select (index) 默认是0）
          
          可以通过move将key从一个数据库转移到另一个数据库

2 redis事务 ： 所有命令串行执行

redis持久化：

1 RDB

默认，不需要配置

在指定的时间间隔内，把内存中的缓存数据快照，存入硬盘，可以设置时间间隔和间隔内访问次数（达到后进行快照）

2 AOF

以日志的形式，把redis的每一次操作记录下来，在redis启动的时候，读取日志并执行



