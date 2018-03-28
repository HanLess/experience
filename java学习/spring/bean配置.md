## bean配置项（常用）：
#### Id:唯一标识
#### Class：实现类
#### Scope：作用域
#### Constructor arguments:构造注入
#### Properties：设值注入
#### Autowiring mode：自动装配模式
#### lazy-initialization mode:懒加载模式
#### initialization/destruction method:初始化，销毁方法

## bean的生命周期
#### 定义
#### 初始化
#### 使用
#### 销毁


#### 初始化（钩子）
1）实现org.springframe.beans.factory.InitializingBean接口，覆盖afterPropertiesSet方法
2）定义init-method方法
#### 销毁（钩子）
1）实现org.springframe.beans.factory.DisposableBean接口，覆盖destory方法
2）定义destory-method方法
#### 全局定义初始化、销毁（钩子）
在xml文件中最外层设置<beans default-init-method="init" default-destory-method="destory"></beans>

## bean中scope用法：
来自：https://blog.csdn.net/u012420654/article/details/52760999

分基础作用域、web作用域

### 基础作用域：

#### singleton：也称单例作用域。
<bean id="animals" class="com.demo.Animals" scope="singleton" />
在每个 Spring IoC 容器中有且只有一个实例，而且其完整生命周期完全由 Spring 容器管理。
对于所有获取该 Bean 的操作 Spring 容器将只返回同一个 Bean。

#### prototype：原型作用域
<bean id="animals" class="com.demo.Animals" scope="prototype" />
每次向 Spring IoC 容器请求获取 Bean 都返回一个全新的Bean。
相对于 singleton 来说就是不缓存 Bean，每次都是一个根据 Bean 定义创建的全新 Bean。

### web作用域

#### request，表示每个请求需要容器创建一个全新Bean。：重点是每个 请求
<bean id="animals" class="com.demo.Animals" scope="request" />

#### session，表示每个会话需要容器创建一个全新 Bean ：重点是每个会话，比如对于每个用户一般会有一个会话，该用户的用户信息需要存储到会话中
<bean id="animals" class="com.demo.Animals" scope="session" />

## Spring 配置使用 - Bean 自动装配
来自：https://blog.csdn.net/u012420654/article/details/52705350
