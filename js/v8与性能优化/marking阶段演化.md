

### 标记策略

marking 是 gc 中重要的一个阶段，v8 的标记策略叫 tri-color marking，即 三色 标记法。

标记从活动的对象（如全局对象）或当前所在的，正在执行的函数开始，这些对象或者函数，被称为 根。

一共有三种标记，用两位数字表示：white(00) ，grey(10)，black(11)

对象被声明后，都是 white，意味着未被引用；当收集器发现此对象，并push进 marking worklist，此对象被标记为 grey；当收集器将此对象从 marking worklist 中推出，并遍历它所有的属性，此对象被标记为 black

当遍历树种没有 grey 的对象，则 marking 阶段结束，此时所有 white 对象都可以被回收

### marking 的演化

#### （1）在主线程中，暂停 js 执行，一次标记完成

#### （2）在主线程中，marking 和 js 交替执行，渐进式完成标记

#### （多线程）

第一种模式，暂停主线程 js 执行，多线程 marking，相当于 （1）的升级版
                
<img src="https://github.com/HanLess/experience/blob/master/js/v8%E4%B8%8E%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96/img/1.png" />


第二种模式，主线程执行 js ，工作线程同时 marking，不会阻塞 js 执行

<img src="https://github.com/HanLess/experience/blob/master/js/v8%E4%B8%8E%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96/img/2.png" />


第二种模式面临一个问题，当主线程与工作线程同时操作一个对象，就会发生冲突。解决冲突的方法如下：

- 主线程对对象的写操作，是原子操作
- 工作线程marking 阶段，改变对象的颜色，也是原子操作
- 写屏障。当发生了黑色节点引用了白色节点的情况，写屏障会强制将被引用的白色节点变成灰色

### 终版

多线程 + 渐进式

<img src="https://github.com/HanLess/experience/blob/master/js/v8%E4%B8%8E%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96/img/3.png" />

 - 主线程从 根 开始遍历对象树，将遍历到的对象变成灰色，即把遍历到的对象放进 worklist
 - 工作线程接手 worklist，将遍历到的所有对象变成黑色，并清空 worklist
 - 主线程在空闲的时候，会参与到 marking 中
 - 当 worklist 被清空后，即所有被遍历到的对象变成黑色，主线程与工作线程同时从根开始 re-scans（重复扫描），这个过程将会发现更多的 white 对象

### 优化效果

最终的 marking 策略，提速 65% - 70%

