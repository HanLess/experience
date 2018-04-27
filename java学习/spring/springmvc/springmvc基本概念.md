dispatcherServlet(前端控制器，用于分发请求)

handlerAdapter(处理请求，将请求转给合适的controller)

handlerInterceptor(拦截器)

handlerMapping(帮助dispatcherServlet获取正确的controller，以及帮助controller匹配拦截器)

handlerExecutionChain(执行链：preHandler -> contrller -> postHandler -> afterCompletion)

modelAndView(model的具体表现，可以使用model，map，但最后都会被转换成modelAndView)

viewResolver(视图解析器，告诉dispatcherServlet需要用哪个视图呈现)

view(呈现页面)


dispatcherServlet 职责：
<ul>
<li>文件上传解析，如果请求类型是multipart将通过MultipartResolver进行文件上传解析；</li>
<li>通过HandlerMapping，将请求映射到处理器（返回一个HandlerExecutionChain，它包括一个处理器、多个HandlerInterceptor拦截器）；</li>
<li>通过HandlerAdapter支持多种类型的处理器(HandlerExecutionChain中的处理器)；</li>
<li>通过ViewResolver解析逻辑视图名到具体视图实现；</li>
<li>本地化解析；</li>
<li>渲染具体的视图等；</li>
<li>如果执行过程中遇到异常将交给HandlerExceptionResolver来解析。</li>
</ul>

