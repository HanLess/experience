http://www.crazyant.net/2124.html

<ul>
  <li>线程池刚创建时，里面没有一个线程。任务队列是作为参数传进来的。不过，就算队列里面有任务，线程池也不会马上执行它们。</li>
  <li>当调用 execute() 方法添加一个任务时，线程池会做如下判断：
      （1）如果正在运行的线程数量小于 corePoolSize，那么马上创建线程运行这个任务；
      （2）如果正在运行的线程数量大于或等于 corePoolSize，那么将这个任务放入队列。
      （3）如果这时候队列满了，而且正在运行的线程数量小于 maximumPoolSize，那么还是要创建线程运行这个任务；
      （4）如果队列满了，而且正在运行的线程数量大于或等于 maximumPoolSize，那么线程池会抛出异常，告诉调用者“我不能再接受任务了”。
  </li>
  <li>当一个线程完成任务时，它会从队列中取下一个任务来执行。</li>
  <li>当一个线程无事可做，超过一定的时间（keepAliveTime）时，线程池会判断，如果当前运行的线程数大于 corePoolSize，那么这个线程就被停掉。所以线程池的所有任务完成后，它最终会收缩到 corePoolSize 的大小。</li>
</ul>

ThreadPoolExecutor线程池执行过程：（1）先比较 corePoolSize （2）比较队列（3）比较 maximumPoolSize（4）阻塞或报错

使用Executors创建线程池：

（1）newFixedThreadPool：创建一个定长有界的线程池，创建的 ThreadPoolExecutor 对象如下：
```
new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
```
使用无界队列 LinkedBlockingQueue，且keepAlive为0，所以不会有现成空闲，线程池也不会拒绝任务（任务排队会阻塞），所以也不会执行 RejectedExecutionHandler
