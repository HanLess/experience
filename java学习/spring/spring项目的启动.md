https://zouzls.github.io/2017/03/29/SpringStart/

<h3>spring启动之前</h3>

1）tomcat会读取web.xml中的 <listener> <context-param>配置，对于spring来说，就是 contextConfigLocation 和 ContextLoaderListener

2）tomcat创建 ServletContext 对象，即没个web项目的context上下文容器，整个web项目都要用到

3）将步骤（1）中读取的信息，写入ServletContext对象中

4）创建 <listener> 中的监听类对象，这个监听类必须实现 ServletContextListener 接口

总结：在tomcat启动后，spring启动前的这个阶段（准备启动spring），主要创建两个对象：
一个是 ServletContext，这是一个web项目的配置记录对象，记录了 listener/context-param/servlet/filter（即web.xml中的配置）
一个是监听类的对象，用来后面启动spring（ServletContextListener）

<h3监听器启动spring</h3>

ServletContextListener 会监听 ServletContext 的状态，如果 ServletContext 状态变化
（个人理解：ServletContext对象创建、初始化完成，记录了所有信息）
会触发监听类的
