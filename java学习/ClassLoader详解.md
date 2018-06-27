https://blog.csdn.net/briblue/article/details/54973413

Bootstrap ClassLoader：加载java核心库

关注点在 ExtClassLoader 和 AppClassLoader，AppClassLoader的parentLoader是ExtClassLoader，ExtClassLoader的parent是null

ExtClassLoader加载：扩展的类加载器，加载目录%JRE_HOME%\lib\ext目录下的jar包和class文件。还可以加载-D java.ext.dirs选项指定的目录

AppClassLoader加载：加载当前应用的classpath的所有类，<h4>即项目中的所有类都是通过这个loader加载</h4>

在类加载器中有个重要的概念：委托机制

```
PropertiesUtil.class.getClassLoader().getResourceAsStream("mmall.properties")
```
classloader可以加载class，也可以加载resource资源，如上面的 mmall.properties，会从中读取各种配置

但是一个classLoader在加载资源的时候会先询问父加载器，此资源是否已经加载（即向上询问）

```
ClassLoader c = PropertiesUtil.class.getClassLoader();
System.out.println(c);
```
打印出：
```
ParallelWebappClassLoader
  context: ROOT
  delegate: false
----------> Parent Classloader:
java.net.URLClassLoader@80ec1f8
```
意思是c加载器是 ParallelWebappClassLoader，他的父加载器是URLClassLoader，加载器的父子层级关系如下：

```
org.apache.catalina.loader.ParallelWebappClassLoader -> java.net.URLClassLoader -> sun.misc.Launcher.AppClassLoader -> sun.misc.Launcher.ExtClassLoader
```
AppClassLoader 会查找我们项目的classpath路径（项目中大多数的配置文件都在此），所以上面的 mmall.properties 会由AppClassLoader加载，并被查到，返回给ParallelWebappClassLoader

如果向上查到 Bootstrap ClassLoade 还没有找到，会从上向下通知加载，具体内容参考上面博客中的 “双亲委托”
