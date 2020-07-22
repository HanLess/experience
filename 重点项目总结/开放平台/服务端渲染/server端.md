服务端的逻辑并不复杂，主要分两步

- 收集 css，data

- 插入 css，data

示例如下

```

// 收集对象
const context = {
    collect: true,
    css: [],
    Methods: [],
    initData: {}
}
// 创建 react 实例，同时把 context 传入
let element = React.createElement(bundle.default(ctx.path, context));

// 第一次 render，此时 css，dataMethods 已经收集完毕
html = ReactDOMServer.renderToString(element);
// 把 css 转成样式字符串，这个 style 就是最终可用的样式
let style = context.css.join('').replace(/\n/g, '')
// 预取数据，如果收集到了 dataMethods，则收集数据，并执行第二次 ReactDOMServer.renderToString，否则跳过此段逻辑
let Data = {}
let Methods = context.Methods
if (Methods.length > 0) {
  await Promise.all(Methods.map((init:any) => {

      return new Promise((resolve, reject) => {
        // 发起异步请求
        init.method().then((data) => {
          // 请求成功
          Data[init.name] = data
          resolve()
        }).catch((e) => {
          // 请求失败
          reject()
        })
      }).catch((e) => {
        // 请求失败后此数据存 null
        Data[init.name] = null
      })

  }))
  // 收集阶段 end

  // 渲染阶段 start
  context.collect = false;
  context.initData = Data
  element = React.createElement(bundle.default(ctx.path, context));
  try {
    html = ReactDOMServer.renderToString(element);
  } catch(e) {
    console.error('第二次 ReactDOMServer.renderToString 错误 ', e)
  }
  // 渲染阶段 end
}

// 最终返回 html ，getTemplate 那到的是前端打包后，最终生成的 index.html
html = await getTemplate()
html = html.
            replace("{{SSR_HTML}}", html).
            replace("{{SSR_CSS}}", style).
            replace("{{SSR_DATA}}", JSON.stringify(Data)).

return html


```
