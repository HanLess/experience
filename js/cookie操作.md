https://segmentfault.com/a/1190000004556040

```
"key=name; expires=Thu, 25 Feb 2016 04:18:00 GMT; domain=ppsc.sankuai.com; path=/; secure; HttpOnly"
```

<h3>expires</h3>

expires选项用来设置“cookie 什么时间内有效”。expires其实是cookie失效日期，expires必须是 GMT 格式的时间（可以通过 new Date().toGMTString()或者 new Date().toUTCString() 来获得）。

如expires=Thu, 25 Feb 2016 04:18:00 GMT表示cookie讲在2016年2月25日4:18分之后失效，对于失效的cookie浏览器会清空。如果没有设置该选项，则默认有效期为session，即会话cookie。这种cookie在浏览器关闭后就没有了。

<h3>domain / path</h3>

一句话概括：某cookie的 domain为“baidu.com”, path为“/ ”，若请求的URL的域名是“baidu.com”或其子域如“api.baidu.com”、“dev.api.baidu.com”，且 URL 的路径是“/ ”或子路径“/home”、“/home/login”，则浏览器会将此 cookie 添加到该请求的 cookie 头部中。domain指定的是其一级和二级域名。
domain的默认值为设置该cookie的网页所在的域名（各级），path默认值为设置该cookie的网页所在的目录

<h3>secure</h3>

secure选项用来设置cookie只在确保安全的请求中才会发送。当请求是HTTPS或者其他安全协议时，包含 secure 选项的 cookie 才能被发送至服务器。

<h3>httpOnly</h3>

这个选项用来设置cookie是否能通过 js 去访问。默认情况下，cookie不会带httpOnly选项(即为空)，所以默认情况下，客户端是可以通过js代码去访问（包括读取、修改、删除等）这个cookie的。当cookie带httpOnly选项时，客户端则无法通过js代码去访问（包括读取、修改、删除等）这个cookie。
在客户端是不能通过js代码去设置一个httpOnly类型的cookie的，这种类型的cookie只能通过服务端来设置。

<h3>用 js 如何设置多个 cookie</h3>

当要设置多个cookie时， js 代码很自然地我们会这么写：
```
document.cookie = "name=Jonh; age=12; class=111";
```
但你会发现这样写只是添加了第一个cookie“name=John”，后面的所有cookie都没有添加成功。所以最简单的设置多个cookie的方法就在重复执行document.cookie = "key=name"，如下：
```
document.cookie = "name=Jonh";
document.cookie = "age=12";
document.cookie = "class=111";
```

<h3>修改 cookie</h3>

要想修改一个cookie，只需要重新赋值就行，旧的值会被新的值覆盖。但要注意一点，在设置新cookie时，path/domain这几个选项一定要旧cookie 保持一样。否则不会修改旧值，而是添加了一个新的 cookie。

<h3>删除 cookie</h3>

删除一个cookie 也挺简单，也是重新赋值，只要将这个新cookie的expires 选项设置为一个过去的时间点就行了。但同样要注意，path/domain/这几个选项一定要旧cookie 保持一样。

