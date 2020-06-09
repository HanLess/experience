### Promise.all

接受一个 promise 实例的数组

（1）数组内的 promise 全部 resolve，则正常返回一个结果数组，结果的顺序与 promise 的顺序一致

（2）如果当中有一个或多个 promise 发生 reject 或 报错，有两种情况

      - 在每个单独的 promise 后 catch，则 Promise.all 正常执行，最终值的内容取决于 catch 中返回什么，因为 catch 也返回一个新的
        promise 对象，这个对象会被 Promise.all 执行
        
      - 在 promise.all 后 catch，会在第一个 reject 处中断，最终返回内容取决于 promise.all 后 catch 的返回内容
      
### 所以在实际应用中，需要在每个 promise 对象后 catch 报错，以免打断整个 all 中的逻辑的执行
