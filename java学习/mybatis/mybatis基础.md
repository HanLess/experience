mybatis的核心类是 sqlSessionFactory ,可以如下创建，其中 mybatis-config.xml 是mybatis的配置文件，可以放在项目的 resources 目录下
```
String resource = "mybatis-config.xml";

Reader reader = Resources.getResourceAsReader(resource);

sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
```

下面的方法可以得到一个 SqlSession 类的实例，
```
sqlSessionFactory.openSession();
```
SqlSession 完全包含了面向数据库执行 SQL 命令所需的所有方法，可以调用sql语句
```
SqlSession sqlSession=sqlSessionFactory.openSession();
StudentDAO studentDAO=sqlSession.getMapper(StudentDAO.class);
Student student=new Student();
student.setName("赵四");
student.setAge(40);
student.setGender(GenderEnum.MALE);
student.setNumber("1960053011");
studentDAO.insertStudent(student);
sqlSession.commit();
```

<h2>SqlSessionFactoryBuilder</h2>

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。

因此 SqlSessionFactoryBuilder 实例的最佳范围是方法范围（也就是局部方法变量）。

你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，

但是最好还是不要让其一直存在以保证所有的 XML 解析资源开放给更重要的事情。

<h2>SqlSessionFactory</h2>

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由对它进行清除或重建。

使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，

多次重建 SqlSessionFactory 被视为一种代码“坏味道（bad smell）”。

因此 SqlSessionFactory 的最佳范围是应用范围。有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

<h2>SqlSession</h2>

每个线程都应该有它自己的 SqlSession 实例。

SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的范围是请求或方法范围。

绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。

也绝不能将 SqlSession 实例的引用放在任何类型的管理范围中，比如 Serlvet 架构中的 HttpSession。

如果你现在正在使用一种 Web 框架，要考虑 SqlSession 放在一个和 HTTP 请求对象相似的范围中。

换句话说，每次收到的 HTTP 请求，就可以打开一个 SqlSession，返回一个响应，就关闭它。

这个关闭操作是很重要的，你应该把这个关闭操作放到 finally 块中以确保每次都能执行关闭。下面的示例就是一个确保 SqlSession 关闭的标准模式：

```
SqlSession session = sqlSessionFactory.openSession();
try {
  // do work
} finally {
  session.close();
}
// 在你的所有的代码中一致性地使用这种模式来保证所有数据库资源都能被正确地关闭。
```

<h2>映射器实例（Mapper Instances）</h2>

映射器是创建用来绑定映射语句的接口。映射器接口的实例是从 SqlSession 中获得的。

因此从技术层面讲，映射器实例的最大范围是和 SqlSession 相同的，因为它们都是从 SqlSession 里被请求的。

尽管如此，映射器实例的最佳范围是方法范围。也就是说，映射器实例应该在调用它们的方法中被请求，用过之后即可废弃。

并不需要显式地关闭映射器实例，尽管在整个请求范围（request scope）保持映射器实例也不会有什么问题，

但是很快你会发现，像 SqlSession 一样，在这个范围上管理太多的资源的话会难于控制。

所以要保持简单，最好把映射器放在方法范围（method scope）内。下面的示例就展示了这个实践：

```
SqlSession session = sqlSessionFactory.openSession();
try {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  // do work
} finally {
  session.close();
}
```

### mybatis注解

https://blog.csdn.net/myNameIssls/article/details/21610249









