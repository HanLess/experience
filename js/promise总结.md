### Promise 执行顺序如下：

1）
```
new Promise(function(resolve,reject){
	console.log("one")
	resolve()
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one four three two
```
2)
```
new Promise(function(resolve,reject){
	console.log("one")
	reject()
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one four three err
```
3)
```
new Promise(function(resolve,reject){
	console.log("one")
	throw new Error();
	
	console.log('four')
}).then(function(){
    console.log("two")
}).catch(function(){
    console.log("err")
})
console.log("three")

// one three err
```

### promise catch用法的特别之处

catch方法返回的还是一个 Promise 对象，因此后面还可以接着调用then方法。

```
const someAsyncThing = function() {
  return new Promise(function(resolve, reject) {
    // 下面一行会报错，因为x没有声明
    resolve(x + 2);
  });
};

someAsyncThing()
.catch(function(error) {
  console.log('oh no', error);
})
.then(function() {
  console.log('carry on');
});
// oh no [ReferenceError: x is not defined]
// carry on
```

如果没有报错，则会跳过catch方法。

```
Promise.resolve()
.catch(function(error) {
  console.log('oh no', error);
})
.then(function() {
  console.log('carry on');
});
// carry on
```


### 对于传统回调存在的问题：

（1）回调函数执行的过早或过晚（可能同步，也可能异步）

（2）回调执行多次、未执行

（3）吞掉报错信息

这些问题可以总结为回调的信任问题，我们把回调交给第三方，回调函数的执行就不受我们（主线程）控制了，不要想着通过读业务代码来判断执行逻辑，当使用三方接口，或业务非常复杂的时候，通过读源码来得知执行的逻辑代价会非常大

除此之外，还有：

（4）回调地狱

（5）无法链式调用，以保障顺序执行

### promise可以解决以上问题：

resolve 执行一定是异步的

通过 status 管理，杜绝了多次调用回调的情况

reject方法会处理报错的情况

链式调用，避免回调地狱，保证执行顺序




