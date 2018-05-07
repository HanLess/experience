package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.exposer;
import org.seckill.entry.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"
                        })
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        System.out.println("====================");
        logger.info("list = {}",list);
    }

    @Test
    public void getById() {
        long seckillId = 4;
        Seckill seckill = seckillService.getById(seckillId);
        System.out.println("====================");
        logger.info("seckill = {}",seckill);
    }

    @Test
    public void SeckillLogic(){
        long seckillId = 4;
        long userPhone = 13000000000L;
        exposer resultExposer = seckillService.exportSeckillUrl(seckillId);
        if(resultExposer.getExposed()){
            String md5 = resultExposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
            System.out.println("====================");
            logger.info("seckillExecution : {}",seckillExecution);
        }else{
            System.out.println("====================");
            logger.info("exposer : {}",resultExposer);
        }
    }

    @Test
    public void exportSeckillUrl() {
        long seckillId = 4;
        exposer resultExposer = seckillService.exportSeckillUrl(seckillId);
        System.out.println("====================");
        logger.info("exposer : {}",resultExposer);
    }

    @Test
    public void executeSeckill() {
        long seckillId = 4;
        long userPhone = 13000000000L;
        String md5 = "cedf24937124034772438ac51aa6025c";

        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);
        System.out.println("====================");
        logger.info("seckillExecution : {}",seckillExecution);
    }
}