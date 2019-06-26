js继承参考文章：http://www.jianshu.com/p/dee9f8b14771

```
class parent{

  constructor(){

    this.name = "parent"

  }

  p_say(){

    console.log("hello")

  }

}

class kid extends parent{}
```

通过观察 new kid() , kid.prototype , new parent() , parent.prototype 可以发现：

1） new parent() 与 new kid() 中的属性一样，kid 通过constructor类继承了parent的属性（相当于调用call）

2） parent.prototype 中的方法是 parent 中constructor之外的方法

3） kid.prototype 为空，只有两个默认属性：

__proto__ ： 指向parent.prototype，起到继承 parent.prototype的作用（跨了一级）

constructor : 指向 kid

#### 当在 class 中使用箭头函数来定义方法时，方法会被存在实例对象的属性下面，而不是 __proto__ 下面

```

class parent{

  constructor(){

    this.name = "parent"

  }

  p_say = () => {
    
    console.log(this)

  }

}
// p_say 方法放在了 p.p_pay 中 , 而不是 p.__proto__ 中
console.log(p = new parent())  
```


