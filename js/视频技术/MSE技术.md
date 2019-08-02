<a href="https://www.jackpu.com/media-source-xi-lie/">Media Source 系列 - 使用 Media Source Extensions 播放视频</a>

<a href="https://juejin.im/entry/5aa64acb6fb9a028b6172adf">Media Source 系列 - 播放 m3u8 文件</a>

<a href="https://juejin.im/post/5b016ca36fb9a07aad17cd13">js实现封装MP4格式文件并下载</a>

<a href="https://lucius0.github.io/2018/02/23/archivers/media-study-010/">音视频学习-MSE基础原理篇</a>


MES技术需要使用 fmp4 格式的视频文件，与 mp4 的区别在于：文件 box 中新增了 segment index box 信息，可以根据字节位置来分片，前端视频中的分片下载播放也是基于这个


## http 相关报文

```
response：

Content-Range: bytes 6079549-6603773/20633151

Content-Length: 524225

request：

Range: bytes=645879-1478445
```

在请求时设置 Range 请求头报文，可以实现分片请求视频资源，收到的数据是 ArrayBuffer 类型

## ArrayBuffer

二进制数组，无法直接进行读写操作，通过视图来读写（TypedArray 视图和 DataView 视图），视图的作用是以指定格式解读二进制数据。

在 Javascript 中，Mp4 的所有 box 全部由通过 new Uint8Array() 实现。

## mp4 文件格式

由多个box组成，主要包括：视频类型（ftyp）、视频数据（mdat）、视频信息（moov）；moof box 仅在流式 MP4中使用（FMP4）

在分析 box 内容的时候，需要读取字节，将会大量运用位运算

<a href="https://github.com/HanLess/experience/blob/master/js/%E8%A7%86%E9%A2%91%E6%8A%80%E6%9C%AF/%E4%BD%8D%E8%BF%90%E7%AE%97_%E6%8A%BD%E5%8F%96%E6%9F%90%E4%B8%AA%E5%AD%97%E8%8A%82.md">读取某几个字节</a>

## box

Box 由 header 和 body 组成，以 32 位的 4 字节整数存储方式存储到内存。

header 前4个字节（32位）为 box size，后面紧跟的 4 个字节为 box type（ftyp moov ...）。

body可以由数据组成，也可以由子box组成。

一个 box 的 size 只可能大于等于 8

如果从 readSize 中解析出来的 mdat size 为 1，则表明此视频比较大，需要 type 后的 8 个字节来计算实际大小

#### Mdat box

Mdat box数据格式单一，无子box。主要分为box header 和box body，box header中存放box size 和box type（mdat），box body中存放所有媒体数据，媒体数据以sample为数据单元。

这里使用时，视频数据中，每一个sample是一个视频帧，存放sample时，需要根据帧数据类型进行拼帧处理后存放。

### Mdat box中，可能会使用到box的large size，当数据足够大，无法用4个字节来描述时，便会使用到large size。在读取MP4文件时，当mdat box的size位为1时，真正的box size在large size中

```
if (this.size === 1) {
  this.size = stream.readByte(4) << 32
  this.size |= stream.readByte(4)
}
```

#### Moov box

只能有一个 Moov box，存放着媒体信息，Moov box 主要包含 mvhd、trak、mvex三种子box。

#### Mvhd box

Mvhd box定义了整个文件的特性，尺寸、类型、版本、生成时间等信息。

timescale：表示本文件的所有时间描述所采用的单位

duration：媒体可播放时长，这个数值的单位与实际时间的对应关系就要通过上面的timescale参数来计算。

duration / timescale = 可播放时长（s）。

#### trak box

一个Track box定义了movie中的一个track。一部movie可以包含一个或多个tracks，它们之间相互独立，各自有各自的时间和空间信息。每个track box 都有与之关联的 mdat box。

使用 mdia.hdlr.handlerType 来判断是音频还是视频 trak。

vide：视频

soun：音频

hint：这个特殊的track并不包含媒体数据，而是包含了一些将其他数据track打包成流媒体的指示信息。

<img src="" />

## 方案

在初始化阶段，把视频信息 box（fype , moov）添加到 sourceBuffer 中，这里的关键是 mp4 box 剥离。<a href='https://github.com/HanLess/mp4-reader'>mp4-reader</a>

通过 timeUpdate 事件驱动加载视频数据片

把 mp4 格式转为 fmp4

视频在分段加载播放的时候，快进可能导致 mediaSource.readyState 变为 end，sourceBuffers.video.timestampOffset = 0 可从新开启

但防止某些极端情况，还是要在 appendBuffer 时判断 mediaSource.readyState 状态，如果是 end，要通过栈结构暂存视频数据 buffer

切换清晰度，通过 video.currentTime 来记录当前播放时间，在切换前要清除旧的 buffer 数据。

