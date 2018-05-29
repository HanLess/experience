package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entry.Seckill;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by daojia on 2018-5-28.
 */
@Component
public class RedisDao {
    private final JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(){
        jedisPool = new JedisPool("localhost",6379);
    }

//    反序列化
    public Seckill getSeckill(long seckillId){
        try{
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckillId:" + seckillId;
                byte[] keyByets = key.getBytes();
//                内部没有序列化，返回二进制码 -> 反序列化拿到需要的内容 -> Object
//                自定义序列化
                byte[] bytes = jedis.get(keyByets);
                if(bytes != null){
//                    空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
//                    被反序列化
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    序列化
    public String putSeckill(Seckill seckill){
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                long seckillId = seckill.getSeckillId();

                String key = "seckillId:" + seckillId;
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                int timeout = 3600;
                String result = jedis.setex(key.getBytes(),timeout,bytes);

                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
        }

        return null;
    }
}
