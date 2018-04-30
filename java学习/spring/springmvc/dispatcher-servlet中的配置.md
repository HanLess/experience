```
<!--视图解析器 prefix是前缀 suffix是后缀，controller返回的字符串会自动拼上前缀和后缀，形成完成的路径，去静态资源中寻找响应的资源-->

<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/views/"/>
    <property name="suffix" value=".jsp"/>
</bean>

<!--配置之后可以访问静态资源(包括jsp)，但是无法访问controller控制的url，且访问资源的路径就是默认的路径，即项目的目录-->

<mvc:default-servlet-handler />

<!--
可以直接将访问的url中的参数（search）带入到controller中，
如果配置了 <mvc:default-servlet-handler /> 或 <mvc:resources mapping="/static/**" location="/views/" />
就必须配置，否则无法使用controller
-->
<mvc:annotation-driven />

<!--访问静态资源 js css img（不可以访问jsp）,跟<mvc:default-servlet-handler />的区别在于可以自定义url-->

<mvc:resources mapping="/static/**" location="/views/" />
```
