package com.mmall.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
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
