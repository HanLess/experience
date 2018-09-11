package org.seckill.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;


@Component("jedisDao")
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

    public void test(){
        valueOps.set("name","han");

        System.out.println(valueOps.get("name"));
    }

}
