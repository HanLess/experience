package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.SuccessKilled;

public interface SuccessKilledDao {

    int insetSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
