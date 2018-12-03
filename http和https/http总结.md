http相关内容繁杂，这里总结一下比较常用的：

<a href="#cache">http缓存</a>

<a href="#woshou">3次握手，4次挥手</a>

<a href="#message">http报文</a>

<a href="#http2">http2</a>

<h2 id="cache">http缓存</h2>

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

客户端http请求流程，图片来源：<a href="https://my.oschina.net/leejun2005/blog/369148">浏览器 HTTP 协议缓存机制详解</a>：

#### 首次请求：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/requestFirst.png" />

#### 再次请求：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/requestAgain.png" />

#### 状态码：

<img src="https://github.com/HanLess/experience/blob/master/http%E5%92%8Chttps/imgs/status.jpg" />

<h2 id="woshou">3次握手，4次挥手</h2>

#### 三次握手（建立链接）

<ul>
  <li>客户端A向服务端B发送 SYN 包，并进入 SYN_SEND 状态，等待服务端的回应</li>
  <li>服务端B收到SYN包后，需要回应，发送 ACK 包，同时发送自己的 SYN 包（序列号加一），即发送 ACK + SYN 包，并进入 SYN_RECV 状态</li>
  <li>客户端收到 ACK+SYN 包后，回应服务端发送 ACK 包，随后客户端与服务端都进入 ESTABLISHED 状态，三次握手完成</li>
</ul>

#### 四次挥手（断开链接）

<ul>
  <li>客户端发送 FIN 包给服务端，用来关闭 客户端 到 服务端 的数据流通</li>
  <li>服务端收到 FIN 包后，发送 ACK 包（序列号加一）</li>
  <li>服务端发送 FIN 包给客户端</li>
  <li>客户端发回 ACK 报文确认</li>
</ul>

#### 建立与断开链接的开销过大，所以HTTP1.1提供了 connection : keep-alive 来进行长链接

<h2 id="message">http报文</h2>

#### 报文种类

<ul>
  <li>请求报文</li>
  <li>响应报文</li>
</ul>

#### 报文结构

<ul>
  <li>起始行：http版本，请求方法，url等</li>
  <li>报文首部：（1）通用首部字段（2）请求首部字段（3）响应首部字段（4）实体首部字段</li>
  <li>报文实体</li>
</ul>

#### 常用的首部字段

<ul>
  <li>缓存相关内容</li>
  <li>实体相关字段：content-type，content-encode 等</li>
  <li>Date，host 等</li>
  <li>内容很多，理解即可</li>
</ul>

<h2 id="http2">http2</h2>

#### http1 / http1.1 存在的问题

<ul>
  <li>TCP 连接数限制</li>
  <li>每个 TCP 连接同时只能处理一个请求 - 响应</li>
  <li>Header 内容多</li>
  <li>明文传输不安全</li>
</ul>

#### http2的特点

<ul>
  <li>二进制传输，最小单位是 帧：（1）减小数据体积（2）不再明文</li>
  <li>多路复用：多个请求共用一个 TCP 链接，每一个请求是一个流，有唯一的流标识（stream identifier），发送各自的帧（帧上携带 stream identifier），接收方接收多个流的帧数据，再根据 stream identifier 把每个流的帧拼起来</li>
  <li>服务端推送</li>
  <li>Header 压缩</li>
  <li>应用层的重置连接：不用进行4次挥手断开链接，可以在不断开 TCP 链接的前提下，不再接收某个请求的流（stream）</li>
  <li>请求优先级设置：可以手动控制请求流的优先级</li>
  <li>流量控制：每个端都可以告诉对方自己可以接收多少数据</li>
</ul>









