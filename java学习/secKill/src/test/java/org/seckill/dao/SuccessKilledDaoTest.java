package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insetSuccessKilled() {
        long seckillId = 4;
        long userPhone = 13000000000L;
        int result = successKilledDao.insetSuccessKilled(seckillId,userPhone);
        System.out.println(result);
    }

    @Test
    public void queryByIdWithSecKill() {
        long seckillId = 4;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(seckillId);
        System.out.println(successKilled);
    }
}