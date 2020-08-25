#### cas( Central Authentication Service )登录模式，将登录剥离成独立服务，成为中心认证服务，


实现 cas 有很多种方式，根据业务场景选择

（1）cas domain 是主域名，这样所有三级域名不同的子域名，也可以共享登录 cookie

 关键点

- 共享 cas 登录 cookie

- 登录后从 cas 拿到 ticket，回到业务域名下，向 cas 验证 ticket 的正确性（安全设计），通过后在业务域名下种登录 cookie

这种模式主域名必须相同

（2）cas server 提供登录页面，登录需要跳转，<a href="https://www.cnblogs.com/JulianHuang/p/11811239.html">深度解读CAS单点登录原理</a>

登录后把登录 cookie 种在 cas 的登录页面上 -> 其他业务域名跳转登录页 -> 如果有 cookie，则已经登录 -> 带着 ticket 跳回，ticket 验证通过后在业务域名种下 cookie

这种模式，一定会跳登录页去验证一下，如果其他业务域名登录过了就直接跳回，否则直接留在这登录

（3）通过 iframe 共享 cookie

所有需要单点登录的域名下，都带着 iframe，且 iframe 是同一个域名（cas url），一个业务登录后，在自己的域名下种 cookie，同时通过 postMessage 把登录信息传给 iframe 的 
cas url，cas url 把登录信息种在自己的域名下 -> 另一个业务域名被访问后，先从 iframe 找登录cookie（也通过 postMessage ），如果有，就种在自己的域名下

这种模式，可以无视域名

（4）三方 cookie，淘宝天猫是这种模式，但未来三方 cookie 会被禁用

三方 cookie 的写入和正常 cookie 一样，但当前域名无法试用（包括网络请求和 js 读取），只有请求三方域名的时候（异步请求、访问页面）才会带着此三方 cookie
