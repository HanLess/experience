```
var my = function(fn){
    var self = this
    self.status = 'pendding'
    self.callback = null
    self.data = null

    var resolve = function(data){
        self.status = 'resolved'
        self.data = data
        if(self.callback){
            self.callback(self.data)
        }
    }

    self.then = function(cb){
        if(self.status == "resolved"){
            return (new my(function(resolve){
                var x = cb(self.data)
                resolve(x)
            }))
        }else{
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
