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

### 要将样式通过模块的方式引入，配置如下

webpack

```

{
  test: lessModuleRegex,
  use: [...getStyleLoaders(
    {
      importLoaders: 2,
      sourceMap: (isEnvTest  ||  isEnvProduction) && shouldUseSourceMap,
      modules: { 
        getLocalIdent: getCSSModuleLocalIdent
      },
    },
  ),{
    loader: "less-loader",
    options: {
        javascriptEnabled: true
    }
  }],
},
```

其中 modules 是关键，具体用法可以参考文档，除了 webpack，也要配置 ts，否则 ts 会报错

<a href="https://github.com/HanLess/experience/blob/master/js/%E5%A6%82%E4%BD%95%E5%9C%A8react%E9%A1%B9%E7%9B%AEtsx%E4%B8%AD%E5%BC%95%E7%94%A8less.md">如何在react项目tsx中引用less</a>

### 最终打包

前端在打包的时候，要分别打包 server，client 端，npm 脚本需要分开

```
"build:development": "NODE_ENV=development node scripts/build.js",
"build:testing": "NODE_ENV=testing node scripts/build.js",
"build:production": "NODE_ENV=production node scripts/build.js",
"test": "node scripts/test.js",
"build:serverproduction": "NODE_ENV=serverproduction node scripts/server.js",
"build:servertesting": "NODE_ENV=servertesting node scripts/server.js",
"build:serverdevelopment": "NODE_ENV=serverdevelopment nodemon scripts/server.js"
```

不同的脚本，用的不同的 webpack 配置，所以平时需要维护两个 webpck.config.js ，不同的配置文件，对应不同的 entry 配置

```
appIndexJs: resolveModule(resolveApp, 'src/clientEntry/index'),

appServerIndexJs: resolveModule(resolveApp, 'src/serverEntry/index.server'),

```

打包结束后，需要把 server output 输出到 server 端的项目里

```
serverAppBuild: resolveApp('../ke-open-platform-server/src/statics/client')

```

打包结束后，要将最终生成的 html 文件复制一份到 server 端，当做 server 生成最终 html 结构时的模板 html，这个 html 有客户端渲染阶段需要的所有 js 引用，这一步非常重要

```
let htmlPath = path.resolve(paths.appBuild, 'index.html')
let serverHtmlPath = path.resolve(paths.serverAppBuild, 'index.htm')
fs.copyFileSync(htmlPath, serverHtmlPath)
```




