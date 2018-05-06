package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/*
* 配置junit与spring整合，junit启动时加载springIOC容器
* */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        long id = 4;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void reduceNumber() {
        long id = 4;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse("2018-05-09 22:36:01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = seckillDao.reduceNumber(id,date);
        System.out.println(result);
    }

    @Test
    public void queryAll() {
        int offset = 0;
        int limit = 10;
        List<Seckill> resultList = seckillDao.queryAll(offset,limit);
        for(Seckill seckill:resultList){
            System.out.println(seckill);
        }
    }
}