package org.seckill.dao;

import org.seckill.entry.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
//    减库存
    int reduceNumber(long seckillId,Date killTime);

    Seckill queryById(long seckillId);

    List<Seckill> queryAll(int offset,int limit);
}
