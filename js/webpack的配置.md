```
{
  entry:{
    app: './src/main.js'
  },
  output : {
    path : path.resolve(__dirname, '../dist'), //通过 HtmlWebpackPlugin 插件生成的html文件存放在这个目录下面
    filename:'static/js/[name].js', //编译生成的js文件存放到制定目录下，[name] 就是 entry 里制定的 app
    chunkFilename: utils.assetsPath('js/cf/[id].js')  // chunkFilename 用来打包异步组件
                                                      //  'my-component': () => import('./my-async-component')
  }
}
```
