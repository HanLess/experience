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
<div>PlatformTransactionManager、TransactionDefinition、TransactionStatus（事务状态管理）</div>


<div>TransactionDefinition定义的事务属性</div>
<div>
  脏读:一个事务读取时，另一个事务已经修改但还未提交，导致读取的数据无效
</div>
<div>
  不可重复读:一个事务中，多次读取到的结果不一致（因为另一个事务在此过程中修改并提交了数据）
</div>
<div>
  幻读:一个事务读取了几行数据后，另一个事务插入了几行数据，在接下来的读取中，第一个事务就会发现多出了几行数据，即幻读
</div>

<div>
  事务的传播行为：解决业务层方法之间相互调用，事务如何传递的问题
</div>
<div>
PROPAGATION_REQUIRED:支持当前事务，如果不存在就新建一个，即保证两个方法在
</div>
<div>
PROPAGATION_REQUIRES_NEW:如果事务存在，挂起当前事务，并新建一个，即保证两个方法不在同一事务里
</div>
<div>
PROPAGATION_NESTED:如果事务存在，则嵌套事务执行
</div>
<div>spring的事务管理</div>
<div>1）transactionTemplate类来实现事务管理（编程式）</div>
<div>2）通过xml来管理（声明式）</div>

https://www.cnblogs.com/zhaozihan/p/6219776.html
