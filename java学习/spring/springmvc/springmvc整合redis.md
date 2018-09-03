xml配置文件
```
<!-- Redis config -->
    <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxTotal" value="10"/>
    </bean>

    <bean id="jedisConnectionFactory"
          class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="hostName" value="localhost" />
        <property name="port" value="6379" />
        <constructor-arg name="poolConfig" ref="jedisPoolConfig"></constructor-arg>
    </bean>

    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <property name="connectionFactory" ref="jedisConnectionFactory"/>
        <property name="defaultSerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="keySerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="valueSerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="hashKeySerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="hashValueSerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
    </bean>
```

java操作类
```
public class JedisDao {
    @Resource(name = "redisTemplate")
    private ListOperations<Serializable, Serializable> listOps;

    @Resource(name = "redisTemplate")
    private HashOperations<Serializable, Serializable, Serializable> hashOps;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Serializable> valueOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Serializable> zsetOps;

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;


    private class user implements Serializable{
        String name = "ha";
    }

    public void test(){
        valueOps.set("name",new user());

        System.out.println(valueOps.get("name"));
    }
}

```

注意：因为版本问题，JedisConnectionFactory 可能会无法注入ioc容器，具体情况不清楚

配置文件中，默认都会使用 StringRedisSerializer 作为序列化的类

在注入 ListOperations HashOperations 等具体操作类的时候，可以直接注入 redisTemplate，因为在spring-data-redis中，相应的注解解释类（如 HashOperationsEditor）
会使用工厂模式创建相应的操作类（setValue）

操作类对应的redis数据类型：

ListOperations：list

HashOperations：hash

ValueOperations：string

SetOperations：set

ZSetOperations：zset

使用 RedisTemplate 来直接操作 key 相关的API

在注入上述的操作类时，指定各自相应的范型，通常来说，key都是String，value指定Serializable，即存的value需要实现 Serializable 接口（String默认实现）


