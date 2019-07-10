<a href="https://www.jackpu.com/media-source-xi-lie/">Media Source 系列 - 使用 Media Source Extensions 播放视频</a>

<a href="https://juejin.im/entry/5aa64acb6fb9a028b6172adf">Media Source 系列 - 播放 m3u8 文件</a>


MES技术需要使用 fmp4 格式的视频文件，与 mp4 的区别在于：文件 box 中新增了 segment index box 信息，可以根据字节位置来分片，前端视频中的分片下载播放也是基于这个


#### http 相关报文

```
Content-Range: bytes 6079549-6603773/20633151

Content-Length: 524225
```
