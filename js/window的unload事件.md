window.onunload 可以监听

- 页面刷新

- 关闭页面

- 关闭浏览器

如果想根据 unload 去记录一些用户行为，要用 localStorage , 因为 sessionStorage 在关闭页面后消失（但是刷新不会消失）
