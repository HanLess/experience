package org.seckill.dao;

import org.seckill.entry.SuccessKilled;

public interface SuccessKilledDao {

    int insetSuccessKilled(long seckillId,long userPhone);

    SuccessKilled queryByIdWithSecKill(long seckillId);
}
