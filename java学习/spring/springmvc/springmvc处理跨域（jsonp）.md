```
@RequestMapping(value = "/test2",method = RequestMethod.GET)
    @ResponseBody
    public Object test2(
            String callback,
            HttpSession session
    ){
        if(StringUtils.isBlank(callback)){
            return "is not jsonp";
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue("ok");
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
```
