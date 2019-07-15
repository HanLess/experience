<a href="https://www.jackpu.com/media-source-xi-lie/">Media Source 系列 - 使用 Media Source Extensions 播放视频</a>

<a href="https://juejin.im/entry/5aa64acb6fb9a028b6172adf">Media Source 系列 - 播放 m3u8 文件</a>

<a href="https://juejin.im/post/5b016ca36fb9a07aad17cd13">js实现封装MP4格式文件并下载</a>


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

二进制数组，无法直接进行读写操作，通过视图来读写（TypedArray 视图和 DataView 视图），视图的作用是以指定格式解读二进制数据。

在 Javascript 中，Mp4 的所有 box 全部由通过 new Uint8Array() 实现。

#### mp4 文件格式

由多个box组成，主要包括：视频类型（ftyp）、视频数据（mdat）、视频信息（moov）；moof box 仅在流式 MP4中使用（FMP4）

在分析 box 内容的时候，需要读取字节，将会大量运用位运算

<a href="https://github.com/HanLess/experience/blob/master/js/%E8%A7%86%E9%A2%91%E6%8A%80%E6%9C%AF/%E4%BD%8D%E8%BF%90%E7%AE%97_%E6%8A%BD%E5%8F%96%E6%9F%90%E4%B8%AA%E5%AD%97%E8%8A%82.md">读取某几个字节</a>

#### box

Box 由 header 和 body 组成，以 32 位的 4 字节整数存储方式存储到内存。

header 前4个字节（32位）为 box size，后面紧跟的 4 个字节为 box type（ftyp moov ...）。

body可以由数据组成，也可以由子box组成。



