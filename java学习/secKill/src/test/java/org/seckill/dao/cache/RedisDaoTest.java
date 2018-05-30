package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.Seckill;
import org.seckill.service.SeckillService;
import org.seckill.service.impl.SeckillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillService seckillService;


    @Test
    public void getSeckill() {
        long seckillId = 1001;
        Seckill seckill = redisDao.getSeckill(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void putSeckill() {
        Seckill seckill = seckillService.getById(1001);
        String result = redisDao.putSeckill(seckill);
        System.out.println(result);
    }

    @Test
    public void testSeckill(){
        long seckillId = 1001;
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            Seckill sseckill = seckillService.getById(1001);
            String result = redisDao.putSeckill(sseckill);
//            if(result.equals("ok")){
                System.out.println("----------------");
                System.out.println(redisDao.getSeckill(seckillId));
//            }
        }else{
            System.out.println("++++++++++++++++");
            System.out.println(seckill);
        }
    }
}