https://blog.csdn.net/jeffleo/article/details/76943067

tomcat中的web项目启动，都会有创建一个 servletContext ,用来存整个web项目的上下文

spring项目启动，如果配置了listener（ContextLoaderListener），会创建 webApplicationContext，作为root context ，servletContext会setAttribute来存 webApplicationContext

springMVC 启动，同样创建 webApplicationContext ，同样会存进 servletContext，但是会执行 setParent，把root context 作为父上下文
