hls : http live streamimg

m3u8 文件格式如下

```
#EXTM3U
#EXT-X-VERSION:3
#EXT-X-TARGETDURATION:17
#EXT-X-MEDIA-SEQUENCE:0
#EXTINF:11.910889,
index0.ts  
#EXTINF:16.601022,
index1.ts  
#EXTINF:5.088756,
index2.ts  
#EXTINF:9.051311,
index3.ts  
#EXTINF:7.466289,
index4.ts  
#EXTINF:14.724022,
index5.ts  
```

请求 ts 资源会返回视频内容

在移动端可以直接播放 m3u8 格式文件，如下

```
<video width="300" height="200" 
       playsinline webkit-playsinline
       autoplay controls preload="auto"
       x-webkit-airplay="true" x5-video-player-fullscreen="true" x5-video-player-typ="h5">
    <source src="http://live.zzbtv.com:80/live/live123/800K/tzwj_video.m3u8" type="application/x-mpegURL">
</video>

```

在 pc 上需要把 m3u8 文件转码，目前成熟的转码工具有：

hls.js

<a href="https://github.com/videojs/videojs-contrib-hls">videojs-contrib-hls.js</a>



