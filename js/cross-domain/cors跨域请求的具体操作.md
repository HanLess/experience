<a href="http://www.ruanyifeng.com/blog/2016/04/cors.html">跨域资源共享 CORS 详解</a>


## 简单请求

只要同时满足以下两大条件，就属于简单请求。

#### 请求方法是以下三种方法之一：

HEAD

GET

POST

#### HTTP的头信息不超出以下几种字段：

Accept

Accept-Language

Content-Language

Last-Event-ID

Content-Type：只限于三个值application/x-www-form-urlencoded、multipart/form-data、text/plain

## 非简单请求

不符合上面两大条件的，都是非简单请求

非简单请求会发出两个请求：预请求（option），正式请求

### 注意：一定要先设置预请求相应，否则浏览器拒绝发起正式请求

## 具体代码

前端

```
const xhr = new XMLHttpRequest()
xhr.open('get', "http://localhost:8080/zhihu2018_sd.mp4")
xhr.responseType = 'arraybuffer'
xhr.withCredentials = true;                       // 要带着 cookie
xhr.setRequestHeader('Range', `bytes=0-20000`)    // 这里如果设置了 range，就会发起一个非简单请求，否则就是简单请求
xhr.send()
```


express

```
这里要给所有请求（包括 options）设置跨域头，让浏览器接到预请求返回内容，发起正式请求

app.all('*', function(req, res, next) {
  res.set("Access-Control-Allow-Origin", "*");
  res.set("Access-Control-Allow-Credentials",true)   // 允许传递 cookie
  res.set("Access-Control-Allow-Headers", "*");      // 这里允许了所有的 Access-Control-Request-Headers
  res.set("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
  if(req.method=="OPTIONS") res.send(200);      /*让options请求快速返回*/
  else  next();
});
```
