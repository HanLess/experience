### ftyp box buffer

这里的内容被固定，是 Uint8Array 类型

```
const content = new Uint8Array([
  0x69, 0x73, 0x6F, 0x6D, // major_brand: isom
  0x00, 0x00, 0x00, 0x01, // minor_version: 0x01
  0x69, 0x73, 0x6F, 0x6D, // isom
  0x61, 0x76, 0x63, 0x31, // avc1
])
```

然后再拼上 8 个字节，分别是 size 和 type

```
return concatTypedArray(
  num2FourBytes(content.length + 8),  
  str2TypedArray(type),
  content
)
```

<a href="https://github.com/HanLess/experience/blob/master/js/%E8%A7%86%E9%A2%91%E6%8A%80%E6%9C%AF/%E4%BD%8D%E8%BF%90%E7%AE%97_%E6%95%B0%E5%AD%97%E8%BD%AC%E5%9B%9B%E4%B8%AA%E5%AD%97%E8%8A%82%E4%BA%8C%E8%BF%9B%E5%88%B6.md">num2FourBytes</a>

<a href="https://github.com/HanLess/experience/blob/master/js/%E8%A7%86%E9%A2%91%E6%8A%80%E6%9C%AF/%E5%AD%97%E7%AC%A6%E4%B8%B2%E8%BD%AC%20Uint8Array.md">str2TypedArray</a>

至此，ftype box 就拼好了
