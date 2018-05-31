package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.Seckill;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDao {
//    减库存
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    Seckill queryById(long seckillId);
//    多个形参的时候，要用mybatis的Param注解，因为java编译之后不会保留形参描述，offset -> args_1，导致mybatis找不到offset
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    void killByProcedure(Map<String,Object> paramMap);
}
