```
var my = function(fn){
    var self = this
    self.status = 'pendding'
    self.callback = null
    self.data = null

    /*
        self.callback 为 true 的情况，说明是异步调用 resolve 方法的：如请求数据后调用
        这时候then已经先执行了
    */
    var resolve = function(data){
        self.status = 'resolved'
        self.data = data
        if(self.callback){
        
            // 实现了promise 异步的功能
            
            setTimeout(function(){
                self.callback(self.data)
            },0)
        }
    }
    
    /*
        如果是同步的过程，resolve ，then 是依次执行的
        如果是异步过程（异步获取数据），then 先执行
        
        注意这里返回的一个新对象（链式调用）
        
        注意：then 中的方法 cb，是在新对象创建的过程中执行的（在对象入参的方法中执行）
        
        then 返回一个新对象，新对象入参中的匿名函数，会用到 self 
        这个 self 是当前的（旧对象的）self，而不是新对象中创建的 self，作用域决定的
    */
    
    self.then = function(cb){
        if(self.status == "resolved"){
            return (new my(function(resolve){
                var x = cb(self.data)
                resolve(x)
            }))
        }else{
            
            /*
                处理异步流程：
                新对象中的 resolve 和 cb 逻辑，都存在 self.callback 中，这样等当前 resolve 执行的时候，
                下一个 resolve 才会执行
            */
        
            return (new my(function(resolve){
                self.callback = function(data){
                    var x = cb(data)
                    resolve(x)
                }
            }))
        }
    }

    fn(resolve)
}        


new my(function(resolve){
    // setTimeout(function(){
        resolve(2)
    // },1000)  
    console.log(1)

}).then(function(data){
    console.log(data)
    return data + 1
}).then(function(_data){
    console.log(_data)
})
```
