<a href="https://www.jackpu.com/media-source-xi-lie/">Media Source 系列 - 使用 Media Source Extensions 播放视频</a>

<a href="https://juejin.im/entry/5aa64acb6fb9a028b6172adf">Media Source 系列 - 播放 m3u8 文件</a>


MES技术需要使用 fmp4 格式的视频文件，与 mp4 的区别在于：文件 box 中新增了 segment index box 信息，可以根据字节位置来分片，前端视频中的分片下载播放也是基于这个


#### http 相关报文

```
response：

Content-Range: bytes 6079549-6603773/20633151

Content-Length: 524225

request：

Range: bytes=645879-1478445
```

在请求时设置 Range 请求头报文，可以实现分片请求视频资源，收到的数据是 ArrayBuffer 类型

#### ArrayBuffer

二进制数组，无法直接进行读写操作，通过视图来读写（TypedArray视图和DataView视图），视图的作用是以指定格式解读二进制数据。

在 griffith 中，mp4 格式的数据，就是用 Unit8Array 来处理的


<a href="https://www.google.com.hk/search?newwindow=1&safe=strict&ei=n_MqXYyUG4nn-AaXzIbgBQ&q=mp4%E8%A7%86%E9%A2%91+box+%E7%BB%93%E6%9E%84%E5%88%86%E6%9E%90+arrayBuffer+Uint8Array&oq=mp4%E8%A7%86%E9%A2%91+box+%E7%BB%93%E6%9E%84%E5%88%86%E6%9E%90+arrayBuffer+Uint8Array&gs_l=psy-ab.3...7209.8753..9343...0.0..0.273.795.0j2j2......0....1..gws-wiz.jVFkLN_Mw9w">mp4视频 box 结构分析 arrayBuffer Uint8Array</a>

<a href="https://juejin.im/post/5b016ca36fb9a07aad17cd13">js实现封装MP4格式文件并下载</a>
