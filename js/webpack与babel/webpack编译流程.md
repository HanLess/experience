<a href="https://longgererer.github.io/2020/04/10/Webpack%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F/#webpack-config-js-%E5%92%8C-shell-%E8%A7%A3%E6%9E%90">webpack生命周期</a>

webpack 编译过程中两个核心对象 compiler , compilation

对这两个对象，官方的解释 <a href="https://www.webpackjs.com/contribute/writing-a-plugin/">Compiler 和 Compilation</a>

可以理解为 compiler 是 webpack 的编译器实体，包含当前 webpack 的所有配置，并负责启动编译。

compilation 是本次编译的实例，包含当前编译版本的所有内容，并控制编译的流程。

webpack 的编译过程由这两个对象控制。

（1）根据 config.js 和 shell 参数创建 webpack 的配置 option
（2）初始化 webpack，创建 compiler 实例
（3）执行 compiler.run 启动编译，并构建 compilation 对象
（4）编译主体流程，细节在后面
（5）根据处理后的 AST 生成处理后的 js 代码
（6）输出

option 初始化 -> compile -> build module -> after compiler -> emit -> after emit

#### 编译主体

 - 解析入口
 - 通过各种 loader 处理源文件，使用 acorn 构建 AST 
 - 在构建 AST 时遇到依赖其他模块，循环处理

webpack 编译过程的核心就是创建 AST ，来实现模块化，loader 在创建的过程被调用来处理源码，而 plugins 则在 compiler 和 compilation 的各种事件钩子上被调用

webpack 的工作过程被 compiler 和 compilation 的事件钩子串起来，可以理解为是被事件驱动的。
