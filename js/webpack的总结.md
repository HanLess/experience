### vue使用异步组件时，webpack的配置

```
{
  entry:{
    app: './src/main.js'
  },
  output : {
    path : path.resolve(__dirname, '../dist'), //通过 HtmlWebpackPlugin 插件生成的html文件存放在这个目录下面
    filename:'static/js/[name].js', //编译生成的js文件存放到制定目录下，[name] 就是 entry 里制定的 app
    
    /*
    chunkFilename 用来打包异步组件
    异步组件用法：
    {
      components : {
        HelloWorld : () => import(/* webpackChunkName: "HelloWorld" */ "@/components/HelloWorld.vue")
      }
    }
    加上 /* webpackChunkName: "HelloWorld" */ 打包出来的文件就是 HelloWorld ，类似注解
    */
    chunkFilename: utils.assetsPath('js/cf/[id].js')  
  }
}
```


### webpack插件总结

#### 从这里搬过来的：https://segmentfault.com/a/1190000016816813 ，总结的非常详细

#### 功能类

（1）html-webpack-plugin：自动生成html

```
new HtmlWebpackPlugin({
  filename: 'index.html', // 生成文件名
  template: path.join(process.cwd(), './index.html') // 模版文件
  minify : false,    // 输出的html文件是否压缩
  inject : true     // 向template或者templateContent中注入所有静态资源
})
```

（2）copy-webpack-plugin：拷贝资源，主要用于静态资源的拷贝，把样式，图片等文件拷贝到编译生成的 dist 文件中

```
new CopyWebpackPlugin([
  {
    from: path.join(process.cwd(), './vendor/'),
    to: path.join(process.cwd(), './dist/'),
    ignore: ['*.json']
  }
])
```

（3）webpack-manifest-plugin && assets-webpack-plugin：俩个插件效果一致，都是生成编译结果的资源单，只是资源单的数据结构不一致而已

```
module.exports = {
  plugins: [
    new ManifestPlugin()
  ]
}


module.exports = {
  plugins: [
    new AssetsPlugin()
  ]
}
```

（4）clean-webpack-plugin：在编译之前清理指定目录指定内容。

```
// 清理目录
const pathsToClean = [
  'dist',
  'build'
]
 
// 清理参数
const cleanOptions = {
  exclude:  ['shared.js'], // 跳过文件
}
module.exports = {
  // ...
  plugins: [
    new CleanWebpackPlugin(pathsToClean, cleanOptions)
  ]
}
```

（5）compression-webpack-plugin：提供带 Content-Encoding 编码的压缩版的资源

```
module.exports = {
  plugins: [
    new CompressionPlugin()
  ]
}
```

（6）progress-bar-webpack-plugin：编译进度条插件

```
module.exports = {
  //...
  plugins: [
    new ProgressBarPlugin()
  ]
}
```

#### 代码相关类

（1）webpack.ProvidePlugin：自动加载模块，如 $ 出现，就会自动加载模块；$ 默认为'jquery'的exports

```
new webpack.ProvidePlugin({
  $: 'jquery',
})
```

（2）webpack.DefinePlugin：定义全局常量

```
new webpack.DefinePlugin({
  'process.env': {
    NODE_ENV: JSON.stringify(process.env.NODE_ENV)
  }
})
```

（3）mini-css-extract-plugin && extract-text-webpack-plugin：提取css样式

对比：

<ul>
  <li>mini-css-extract-plugin 为webpack4及以上提供的plugin，支持css chunk</li>
  <li>extract-text-webpack-plugin 只能在webpack3 及一下的版本使用，不支持css chunk</li>
</ul>  

```
// extract-text-webpack-plugin：

const ExtractTextPlugin = require("extract-text-webpack-plugin");
module.exports = {
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ExtractTextPlugin.extract({
          fallback: "style-loader",
          use: "css-loader"
        })
      }
    ]
  },
  plugins: [
    new ExtractTextPlugin("styles.css"),
  ]
}
```

```
mini-css-extract-plugin：

const MiniCssExtractPlugin = require("mini-css-extract-plugin");
module.exports = {
    module: {
    rules: [
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              publicPath: '/'  // chunk publicPath
            }
          },
          "css-loader"
        ]
      }
    ]
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: "[name].css", // 主文件名
      chunkFilename: "[id].css"  // chunk文件名
    })
  ]
}
```

#### 编译结果优化类

（1）uglifyjs-webpack-plugin：用于js压缩

```
module.exports = {
  //...
  optimization: {
    minimizer: [new UglifyJsPlugin({
      cache: true,   // 开启缓存
      parallel: true, // 开启多线程编译
      sourceMap: true,  // 是否sourceMap
      uglifyOptions: {  // 丑化参数
        comments: false,
        warnings: false,
        compress: {
          unused: true,
          dead_code: true,
          collapse_vars: true,
          reduce_vars: true
        },
        output: {
          comments: false
        }
      }
    }]
  }
};
```

（2）optimize-css-assets-webpack-plugin：css压缩，主要使用 cssnano 压缩器

```
module.exports = {
  //...
  optimization: {
    minimizer: [new OptimizeCssAssetsPlugin({
      cssProcessor: require('cssnano'),   // css 压缩优化器
      cssProcessorOptions: { discardComments: { removeAll: true } } // 去除所有注释
    })]
  }
};
```

（3）webpack-md5-hash：使你的chunk根据内容生成md5，用这个md5取代 webpack chunkhash。

```
var WebpackMd5Hash = require('webpack-md5-hash');
 
module.exports = {
  // ...
  output: {
    //...
    chunkFilename: "[chunkhash].[id].chunk.js"
  },
  plugins: [
    new WebpackMd5Hash()
  ]
};
```








