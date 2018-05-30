package org.seckill.service;

import org.seckill.dto.SeckillExecution;
import org.seckill.dto.Exposer;
import org.seckill.entry.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;
//站在使用者角度设计业务接口

/*
* 1 方法粒度
* 2 参数
* 3 返回类型 （类型 / 异常）
* */

public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

//    查看是否开启秒杀
    Exposer exportSeckillUrl(long seckillId);

    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;

    SeckillExecution executeWithProcedure(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
