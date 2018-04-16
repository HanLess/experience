事务特性
<br />
原子性：
<br />
事务不可分割
<br />
隔离性：
<br />
数据库被并发访问时，一个事务不能被其他事务干扰，不同事务之间保持隔离
<br />
持续性：
<br />
事务一旦提交，数据被永久的保存到数据库中
<br />
一致性：
<br />
事务的前后，数据的完整性保持一致

spring中操作事务的三个常用接口：
<div>PlatformTransactionManager、TransactionDefinition、</div>



<div>脏读:</div>
不可重复读、幻读
