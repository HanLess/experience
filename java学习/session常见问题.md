https://www.cnblogs.com/lonelydreamer/p/6169469.html



    1、session在何时被创建 
    
    一个常见的误解是以为session在有客户端访问时就被创建，然而事实是直到某server端程序调用HttpServletRequest.getSession(true)这样的语句时才被创建，注意如果JSP没有显示的使用 <%@page session="false"%> 关闭session，则JSP文件在编译成Servlet时将会自动加上这样一条语句HttpSession session = HttpServletRequest.getSession(true);这也是JSP中隐含的session对象的来历。 

    由于session会消耗内存资源，因此，如果不打算使用session，应该在所有的JSP中关闭它。 

    2、session何时被删除 

    综合前面的讨论，session在下列情况下被删除a.程序调用HttpSession.invalidate();或b.距离上一次收到客户端发送的session id时间间隔超过了session的超时设置;或c.服务器进程被停止（非持久session） 

    3、如何做到在浏览器关闭时删除session 

    严格的讲，做不到这一点。可以做一点努力的办法是在所有的客户端页面里使用javascript代码window.oncolose来监视浏览器的关闭动作，然后向服务器发送一个请求来删除session。但是对于浏览器崩溃或者强行杀死进程这些非常规手段仍然无能为力。 

    4、有个HttpSessionListener是怎么回事 

    你可以创建这样的listener去监控session的创建和销毁事件，使得在发生这样的事件时你可以做一些相应的工作。注意是session的创建和销毁动作触发listener，而不是相反。类似的与HttpSession有关的listener还有HttpSessionBindingListener，HttpSessionActivationListener和HttpSessionAttributeListener。 

    5、存放在session中的对象必须是可序列化的吗 

    不是必需的。要求对象可序列化只是为了session能够在集群中被复制或者能够持久保存或者在必要时server能够暂时把session交换出内存。在Weblogic Server的session中放置一个不可序列化的对象在控制台上会收到一个警告。我所用过的某个iPlanet版本如果session中有不可序列化的对象，在session销毁时会有一个Exception，很奇怪。 

    6、如何才能正确的应付客户端禁止cookie的可能性 

    对所有的URL使用URL重写，包括超链接，form的action，和重定向的URL，具体做法参见[6] http://e-docs.bea.com/wls/docs70/webapp/sessions.html#100770 

    7、开两个浏览器窗口访问应用程序会使用同一个session还是不同的session 

    参见第三小节对cookie的讨论，对session来说是只认id不认人，因此不同的浏览器，不同的窗口打开方式以及不同的cookie存储方式都会对这个问题的答案有影响。 

    8、如何防止用户打开两个浏览器窗口操作导致的session混乱 

    这个问题与防止表单多次提交是类似的，可以通过设置客户端的令牌来解决。就是在服务器每次生成一个不同的id返回给客户端，同时保存在session里，客户端提交表单时必须把这个id也返回服务器，程序首先比较返回的id与保存在session里的值是否一致，如果不一致则说明本次操作已经被提交过了。可以参看《J2EE核心模式》关于表示层模式的部分。需要注意的是对于使用javascript window.open打开的窗口，一般不设置这个id，或者使用单独的id，以防主窗口无法操作，建议不要再window.open打开的窗口里做修改操作，这样就可以不用设置。 

    9、为什么在Weblogic Server中改变session的值后要重新调用一次session.setValue 
    
    做这个动作主要是为了在集群环境中提示Weblogic Server session中的值发生了改变，需要向其他服务器进程复制新的session值。 

    10、为什么session不见了 

    排除session正常失效的因素之外，服务器本身的可能性应该是微乎其微的，虽然笔者在iPlanet6SP1加若干补丁的Solaris版本上倒也遇到过；浏览器插件的可能性次之，笔者也遇到过3721插件造成的问题；理论上防火墙或者代理服务器在cookie处理上也有可能会出现问题。 
