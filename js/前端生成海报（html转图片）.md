<a href="http://html2canvas.hertzen.com/documentation">html2canvas</a>

前端根据 dom 结构生成图片：dom -> canvas -> base64 -> <a>标签 download 属性下载

### 注意

如果 dom 中有图片，且跨域获取（大部分图片都在 cdn 中，加 cors 头不现实），canvas 使用这个图片会被 “污染”，

结果就是 toBlob(), toDataURL() 或 getImageData() 等方法无法获取图片的信息，导致 dom 中的图片无法在 canvas 中展示

### 解决办法

- 图片放本地

- 在 cdn 上的图片，可以使用 js 请求后，通过 canvas 转为 base64 格式，再放回 dom 中的 img.src 中进行 to canvas 转换，代码如下

```
img2base64(url, crossOrigin) {
    return new Promise(resolve => {
        const img = new Image();

        img.onload = () => {
            const c = document.createElement('canvas');

            c.width = img.naturalWidth;
            c.height = img.naturalHeight;

            const cxt = c.getContext('2d');

            cxt.drawImage(img, 0, 0);
            // 得到图片的base64编码数据
            resolve(c.toDataURL('image/png'));
        };

        crossOrigin && img.setAttribute('crossOrigin', crossOrigin);
        img.src = url;
    });
}
```

### a 标签 download 属性的兼容

a 标签 download 属性属于 html5 的新特性，在移动端有个别浏览器不兼容（safari 等），需要判断当前环境，如果不兼容，可以选择让用户长按图片保存

### html2canvas 只支持部分样式，要根据官网写需要转换的 dom 结构

### 实际操作踩坑

####  图片模糊

默认的 canvas 尺寸小，像素少，会有图片模糊的现象，只需要根据当前设备的像素比（window.devicePixelRatio），创建一个canvas 传如 html2canvas 即可

#### 图片画出来怎么不见了

原因如上

#### 倒角

border-radius 必须 ≤ 短边长度的一半，并且是具体数值，否则可能会出现奇妙的效果。

另外使用伪元素实现 0.5px 边框也可能会出现奇妙效果，建议直接使用 border 属性

0.4.1 版本中需要做圆形图片只能置为背景图，img 不支持绘制 border-radius，0.5.0 中则无此限制

#### 虚线

使用 border-style: dashed/dotted 无效

<a href="http://caibaojian.com/h5-download.html">前端js保存页面为图片下载到本地的坑</a>









