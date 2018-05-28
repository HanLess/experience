package org.seckill.dao.cache;

import org.seckill.entry.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by daojia on 2018-5-28.
 */
public class RedisDao {
    private final JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(long seckillId){
        try{
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String putSeckill(Seckill seckill){

        return null;
    }
}
