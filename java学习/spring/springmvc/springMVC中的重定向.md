http://panyongzheng.iteye.com/blog/2201603

return "redirect:your path"

your path：可以是已经定义的 url ，也可以是静态资源的路径（只要是有返回的路径就可以），拼接参数的方法见上面的文章

http的报文信息里，response 的返回码是 302，Location的值是 your path。
