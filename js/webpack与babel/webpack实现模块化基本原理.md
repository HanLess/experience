参考：<a href="https://zhuanlan.zhihu.com/p/58151131">理解webpack原理，手写一个100行的webpack</a>

```
// entry.js
import message from './message.js';
console.log(message);

// message.js
import {name} from './name.js';
export default `hello ${name}!`;

// name.js
export const name = 'world';

//bundler.js 
// 读取文件信息，并获得当前js文件的依赖关系
function createAsset(filename) {//代码略}
// 从入口开始分析所有依赖项，形成依赖图，采用广度遍历
function createGraph(entry) {//代码略}
// 根据生成的依赖关系图，生成浏览器可执行文件
function bundle(graph) {//代码略}
```

关键方法就是 createAsset，createGraph，bundle

- createAsset 用来解析js文件，形成 AST 节点，每个节点包括 id，path，dependence，code 等信息
- createGraph 从入口文件开始，遍历项目的 js 文件，形成依赖图
- bundle 用来生成最终代码

bundle 最终生成的代码大概如下

```
(function (modules) {
  function require (id) {
  
    const [fn, mapping] = modules[id]
    
    function localRequire (relativePath) {
      return require(mapping[relativePath])
    }
    
    const module = { exports: {} }
    fn(localRequire, module, module.exports)
    return module.exports
  }
  require(0)
})(
  0: [ 
    function(require, module, exports) {
        // do something
        var _message = require('./message.js')
        // do someting
    }, { './message.js': 1 }
  ],
  1: [
    function() {
      // message.js 
      // do something
    }
  ]
)


```

生成的最终代码中，核心方法就是 require，module.exports 符合
