因为使用了 router，client 与 server 打包要有不同的入口

client entry 就是正常的 react 根节点，如下

```
const Routers: FC = () => {
  return (
    <Router>


          <Switch>
            {routes.map((route) => (
              <Route {...route} />
            ))}
            <Redirect from="/*" to="/"></Redirect>
          </Switch>


    </Router>
  )
}
```

server entry 需要把 BrowserRouter 换成 StaticRouter ，如下

```
const Routers: FC<any> = (props) => {
  return (
    <StaticRouter location={props.path} context={props.context}>
        <Switch>
          {routes.map((route) => (
              <Route {...route} />
          ))}
          <Redirect from="/*" to="/"></Redirect>
        </Switch>
    </StaticRouter>
  )
}
```

location 就是匹配到的 path，不可变，context 是服务端传过来的参数，下面的各个组件可以通过 this.props.staticContext 来获取里面的内容





