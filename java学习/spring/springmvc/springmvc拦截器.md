
配置xml文件：
```
<mvc:interceptors>
    <!-- 这个拦截器针对 ／hello 路径 -->
    <mvc:interceptor>
        <mvc:mapping path="/hello"/>
        <bean class="one.Interceptor"></bean>
    </mvc:interceptor>
    
    <!-- 这个拦截器针对所有路径 -->
    <bean class="one.Interceptor2"></bean>
</mvc:interceptors>
```

拦截器类：
```
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre handle");
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post handle");
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after handle");
    }
}
```
执行顺序：preHandle -> controller -> postHandle -> afterComletion

三个方法中都可以获得请求的 reqeust对象和 返回的 response 对象，并对其进行操作

pereHandle：返回boolean值，表示是否要将当前的请求拦截下来，如果true，则请求继续，如果false，请求终止（preHandle执行完，controller以及后续拦截器方法都不执行）

三个方法都有入参 Object handler，表示的是被拦截的类的实例对象，即 /hello 对应的 controller 类的实例。

postHandle：入参 ModelAndView modelAndView，可以修改 controller 中的 Model。


另一个实现拦截器类的方法：继承 webRequestIntercetor 接口

区别：webRequestIntercetor接口的preHandle方法没有返回值，即不能阻止请求继续执行。

应用场景：权限验证，是否登录


