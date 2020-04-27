### 场景

同时发起多个请求（fetch），要求只能并发 3 个，每执行完一个再执行下一个，直到全部执行完，返回所有结果

### 实现

```
function fetch (name) {
    return new Promise(function(resolve, reject){
        setTimeout(() => {
            resolve(name)
        }, 1000)
    })
}
let a = 'a'
let b = 'b'
let c = 'c'
let d = 'd'
let e = 'e'
let f = 'f'
let g = 'g'
let h = 'h'

const list = [a,b,c,d,e,f,g,h]

function FetchAll (plist, limit) {
    let count = 0;
    let finish = 0;
    let listLen = list.length;
    const result = []

    function FetchNext (index, resolve) {
        let nitem = plist.shift();
        if (nitem) {
            fetch(nitem).then((data) => {
                console.log(data, index)
                finish++
                result[index] = data;
                if (finish == listLen) {
                    resolve(result)
                } else {
                    FetchNext(count, resolve)
                }
            })
            count++;
        }
    }

    return new Promise((resolve) => {
        let len = Math.min(plist.length, limit);
        for (let i = 0;i < len;i ++) {
            count ++;
            let item = plist.shift();
            fetch(item).then((data) => {
                console.log(data, i)
                result[i] = data;
                finish++
                if (finish == listLen) {
                    resolve(result)
                } else {
                    FetchNext(count, resolve);
                }
            })
        }
    })
}

FetchAll(list, 3).then((res) => {
    console.log(res)
})
```

### Promise.all 的实现

以此类推可以实现 Promise.all ，只需要去掉 limit 限制即可



