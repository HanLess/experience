vue项目build之后的 index.html 用到了 prefetch , preload , 这里记录一下用法，参考文章：https://segmentfault.com/a/1190000011577248

### dns-prefetch

非常简单，效果立竿见影，加快页面加载时间，多用于预解析CDN的地址的DNS

```
<!--在head标签中，越早越好-->
<link rel="dns-prefetch" href="//example.com">
```

### preload

浏览器会在遇到如下link标签时，立刻开始下载main.js(不阻塞parser)，并放在内存中，但不会执行其中的JS语句。
只有当遇到script标签加载的也是main.js的时候，浏览器才会直接将预先加载的JS执行掉。

```
<link rel="preload" href="/main.js" as="script"> 或 <link rel="preload" href="/main.css" as="style">

// 在 head 中加入上面的 link 后，还需要在后面加如下标签，js 或 css 才能起作用

<script src=""/main.js""></script> 或 <link href="/main.css" rel="stylesheet" />
```

### prefetch

浏览器会在空闲的时候，下载main.js, 并缓存到disk。当有页面使用的时候，直接从disk缓存中读取。
其实就是把决定是否和什么时间加载这个资源的决定权交给浏览器。

如果prefetch还没下载完之前，浏览器发现script标签也引用了同样的资源，浏览器会再次发起请求，这样会严重影响性能的，加载了两次，
所以不要在当前页面马上就要用的资源上用prefetch，要用preload。

```
<link href="main.js" rel="prefetch">
```

例子：

```
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Faster</title>
  <link rel="dns-prefetch" href="//cdn.com/">
  <link rel="preload" href="//js.cdn.com/currentPage-part1.js" as="script">
  <link rel="preload" href="//js.cdn.com/currentPage-part2.js" as="script">
  <link rel="preload" href="//js.cdn.com/currentPage-part3.js" as="script">

  <link rel="prefetch" href="//js.cdn.com/prefetch.js">
</head>
<body>

<script type="text/javascript" src="//js.cdn.com/currentPage-part1.js" defer></script>
<script type="text/javascript" src="//js.cdn.com/currentPage-part2.js" defer></script>
<script type="text/javascript" src="//js.cdn.com/currentPage-part3.js" defer></script>
</body>
</html>
```

首先，Parser在遇到head中preload时开始下载JS，读到script标签的时候，如果已经下载完了，直接按顺序执行之。如果没下载完，则会等到下载完再执行。这样就可以在刚进入页面时开始非阻塞的下载JS代码了。

其次，页面会在空闲时，加载prefetch的JS，如果之后页面发生跳转，跳转的目标页面引入了prefetch.js，浏览器会直接从disk缓存中读取执行。

将script标签依然放在</body>之前，并增加defer标签，确保老浏览器兼容，并在所有DOM元素解析完成之后执行其中的代码。

至此，完美的HTML结构出炉了。

