https://segmentfault.com/a/1190000004556040

```
"key=name; expires=Thu, 25 Feb 2016 04:18:00 GMT; domain=ppsc.sankuai.com; path=/; secure; HttpOnly"
```

<h3>expires</h3>

expires选项用来设置“cookie 什么时间内有效”。expires其实是cookie失效日期，expires必须是 GMT 格式的时间（可以通过 new Date().toGMTString()或者 new Date().toUTCString() 来获得）。

如expires=Thu, 25 Feb 2016 04:18:00 GMT表示cookie讲在2016年2月25日4:18分之后失效，对于失效的cookie浏览器会清空。如果没有设置该选项，则默认有效期为session，即会话cookie。这种cookie在浏览器关闭后就没有了。

<h3>domain / path</h3>

一句话概括：某cookie的 domain为“baidu.com”, path为“/ ”，若请求的URL的域名是“baidu.com”或其子域如“api.baidu.com”、“dev.api.baidu.com”，且 URL 的路径是“/ ”或子路径“/home”、“/home/login”，则浏览器会将此 cookie 添加到该请求的 cookie 头部中。domain指定的是其一级和二级域名
