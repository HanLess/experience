<a href="https://github.com/HanLess/fmp4Conversor">fmp4Conversor</a>

MSE 要求视频格式是 fmp4，但大部分用户上传的视频是 mp4 格式，这就需要我们的播放器提供在线转码的功能

分两步：

（1）封装 fype moov box

（2）封装 moof mdat box

第一步相对简单，第二步需要通过 moov trak 中的 sample 信息，把视频数据分片，封装到相应的 moof mdat 中，可以理解为：用 moof 代替了 trak 中的 sample 信息

然后 mdat 从一个整块数据，分成数个 segment。

在 MSE 中，MediaSource.addSourceBuffer 可以添加音频和视频

```
video: 'video/mp4; codecs="avc1.42E01E"',
audio: 'audio/mp4; codecs="mp4a.40.2"'
```

得到两个 sourceBuffer，在之后的转码、添加数据过程过，分别针对 音频/视频 trak 提取出相应的 moof mdat box，并通过 sourceBuffer.appendBuffer 
添加到 MediaSource 中，音频和视频轨道分开处理、转码、添加，<video> 会自动完成合成


