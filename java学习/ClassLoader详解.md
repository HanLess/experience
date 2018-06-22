https://blog.csdn.net/briblue/article/details/54973413

关注点在 ExtClassLoader 和 AppClassLoader，AppClassLoader的parentLoader是ExtClassLoader

ExtClassLoader加载：扩展的类加载器，加载目录%JRE_HOME%\lib\ext目录下的jar包和class文件。还可以加载-D java.ext.dirs选项指定的目录

AppClassLoader加载：加载当前应用的classpath的所有类，<h4 style="display:inline-block">即项目中的所有类都是通过这个loader加载</h4>
