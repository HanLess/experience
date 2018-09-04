服务提供者：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans  
        http://www.springframework.org/schema/beans/spring-beans.xsd  
        http://code.alibabatech.com/schema/dubbo  
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
    <!-- 提供方应用信息，用于计算依赖关系 -->
    <dubbo:application name="coder-center-provider" />
    <!-- 使用zookeeper注册中心暴露服务地址 -->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181" />
     <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <!-- 声明需要暴露的服务接口 -->
    <dubbo:service interface="com.shengfeng.user.service.UserService"
        ref="userService" />
    <bean id="userService" class="com.shengfeng.user.service.impl.UserServiceImpl" />
</beans>
```

服务消费者：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
    xsi:schemaLocation="http://www.springframework.org/schema/beans  
        http://www.springframework.org/schema/beans/spring-beans.xsd  
        http://code.alibabatech.com/schema/dubbo  
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
     <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样  -->
    <dubbo:application name="consumer-appserver" />
    <!-- 使用zookeeper注册中心暴露发现服务地址 -->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181" default="true"/>
    <!-- 引入服务接口 -->
    <dubbo:reference id="userService" interface="com.shengfeng.user.service.UserService" />
</beans>
```

注：消费者的pom中，要手动把提供者的接口jar包引进来。

