针对 client entry 和 server entry，需要有不同的 webpack 配置

### 因为服务端获取包只能获取一个，无法像客户端那样分包加载，所以要修改打包策略

在 webpack 中，可以修改 optimization.splitChunks 来改变打包策略

- client，全部分包，分包规则按配置

```
splitChunks: {
  chunks: 'all',
  name: false,
},
```

- server，只有异步组件才分包，而我们的 server entry 不适用组件的懒加载，所以只会输出一个包

```
splitChunks: {
  chunks: 'async',
  name: true
}
```

### 需要收集样式，需要引入 isomorphic-style-loader 包

引入 isomorphic-style-loader 后，应用在所有样式文件上（css，less）

在组件中，引入的 style，就可以执行 style._getCss()，来获取样式文件中的内容，通过字符串返回

```
import style from "./home.module.less"

// getCss 是封装好的装饰器
@getCss(style)
class Home extends Component<CommonProps> {

    ...
    
    // 或者
    constructor (props) {
        super(props)
        
        console.log(style._getCss)
    }
    ...
}
```





