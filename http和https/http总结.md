http相关内容繁杂，这里总结一下比较常用的：

<a href="#cache">http缓存</a>
<a></a>
<a></a>
<a></a>

<h2 id="cache">http缓存</h2>

缓存内容参考：<a href="https://my.oschina.net/leejun2005/blog/369148">浏览器 HTTP 协议缓存机制详解</a>

强制缓存：（1）expires （2）cache-control

协商缓存：（1）Last-Modified （2）Etag

#### expires

<ul>
  <li>存在于 response</li>
  <li>时间戳，表示缓存过期时间</li>
  <li>缺陷：依赖客户端本地时间</li>
  <li>http1.0的内容，基本被替代</li>
</ul>

#### Cache-Control

<ul>
  <li>存在于 response</li>
  <li>http1.1内容，替换了 expires</li>
</ul>

常用参数：

```
（1）max-age：缓存的最大时间（秒）
（2）no-cache：绕过浏览器缓存，直接请求服务器（可以使用服务器缓存）
（3）no-store：绕过浏览器，服务器缓存，即不使用缓存
```

#### Last-Modified 与 If-Modified-Since

<ul>
  <li>Last-Modified 存在于 response</li>
  <li>If-Modified-Since 存在于 request</li>
  <li>用法：Last-Modified 记录了资源的最后修改时间，If-Modified-Since 记录了服务端返回的最后修改时间，服务端接收请求后将两者比对，如果资源更新了，则返回新资源，status=200，否则返回本地缓存，status=304</li>
  <li>要跟 cache-control 配合使用</li> 
  <li>缺陷：如果资源更改过快（毫秒级），Last-Modified 无法响应；资源内容并没有任何变化（如定期生成），但Last-Modified却改变了</li>
</ul>

#### Etag 与 If-None-Match

<ul>
  <li>Etag 存在于 response</li>
  <li>If-None-Match 存在于 request</li>
  <li>用法：Etag 记录了资源的唯一标识（文件内容，大小等的hash值，有不同实现），If-None-Match 记录了此资源的 Etag，服务端进行比对，如果一样则返回304，使用本地缓存，否则返回新资源，status=200</li>
  <li>与 Last-Modified 很像，把修改时间改成了唯一标识</li>
  <li>同样需要与 cache-control 配合使用</li>
  <li>Etag 在感知文件变化上比 Last-Modified 更加准确，优先级也更高。当 Etag 和 Last-Modified 同时存在时，以 Etag 为准。</li> 
</ul>

#### 为什么 Last-Modified 与 Etag 要与 Cache-control 配合使用

如果响应头中有 Last-modified 而没有 Expire 或 Cache-Control 时，
浏览器会有自己的算法来推算出一个时间缓存该文件多久，
不同浏览器得出的时间不一样，所以 Last-modified 要记得配合 Expires/Cache-Control 使用。

客户端http请求流程：

#### 首次请求：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/requestFirst.png" />

#### 再次请求：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/requestAgain.png" />

#### 状态码：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/status.jpg" />


### 常用的状态码


### 3次握手，4次挥手


### http报文


### http2
