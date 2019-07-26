在分析 griffith 源码的时候，发现了一个包管理工具 lerna

### 作用

在同时开发、管理多个模块的时候，可以使用 lerna 控制各个模块单独或批量的执行 npm script 命令

还有一个重要的功能，就是使用 lerna link 命令可以使各个模块可以相互引用，而不用改变代码，比如有 ModuleB：

```
import ModuleA from 'ModuleA'

export default function(){
  ModuleA()
}
```

可以用引用 npm 包的方法来引用本地 packages 下面的 ModuleA，这样的好处非常明显，就是可以各个模块联合调试

总之，lerna 就是一个模块管理工具
