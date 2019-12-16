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

const list = [a,b,c,d,e]
const result = []

function FetchNext (item, ind) {
    return fetch(item).then((data) => {
        console.log(data, ind)
        result[ind] = data;
    })
}

function FetchAll (plist, limit) {
    let count = 0;
    let finish = 0;
    let listLen = plist.length;
    return new Promise((resolve) => {
        if (plist.length > limit) {
            for (let i = 0;i < limit;i ++) {
                count ++;
                (function(ind){
                    let item = plist.shift();

                    fetch(item).then((data) => {
                        finish++;
                        result[ind] = data;
                        console.log(data,ind)
                        let nitem = plist.shift();
                        if (nitem) {
                            FetchNext(nitem, count).then(() => {
                                finish++
                                if (finish == listLen) {
                                    resolve(result)
                                }
                            });
                            count++
                        }
                    })

                })(i)
            }
        }
    })
}

FetchAll(list, 3).then((res) => {
    console.log(res)
})
```
