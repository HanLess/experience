前端使用formData的情况，如果要同时上传多张图片，代码如下
```
$("#upload").click(function () {
        var formData = new FormData();
        var fileList = $('#file')[0].files

        Array.prototype.forEach.call(fileList,function (val,index) {
            formData.append('file', val);
        })

        $.ajax({
            url : "/manage/product/upload",
            data : formData,
            processData: false,
            contentType: false,
            type : "post"
        })
    })
```
注意需要循环调用 append 方法，key值为控制器接收的名字
控制器代码如下
```
@RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> upload(@RequestParam(value = "file") MultipartFile[] file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file[0],path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }
```
对于接收多张图，可以直接用数组接收
