使用js实现动画的时候，可以使用 requestAnimationFrame 替代 定时器

requestAnimationFrame 具体什么时候执行由操作系统决定，这样可以避免定时器带来的时间误差

递归调用，然后自己模拟定时器效果

```
var delay = 1000
var last = Date.now()
var now = 0

var animation = function(){
    now = Date.now();
    if(now - last > delay){
        last = now
        console.log(0)
    }

    requestAnimationFrame(animation)
}
animation()
```
