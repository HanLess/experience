

#### 标记策略

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
                
                     marking    js                                          
<div>main thread    ---------->------------->-------------------></div>

                      marking                                   
<div>worker thread  ---------->--------------------------------></div>

                     marking                                    
<div>worker thread  ---------->--------------------------------></div>


第二种模式

                         js                                       
<div>main thread    ------------------>---------------------------></div>

                     marking                                        
<div>worker thread  -------------->--------------------------------></div>

                      marking                                        
<div>worker thread  -------------->--------------------------------></div>




