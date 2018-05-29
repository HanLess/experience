package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entry.Seckill;
import org.seckill.entry.SuccessKilled;
import org.seckill.enums.SeckillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

//    md5盐字符串
    private final String slat = "test_slat";

    @Override
    public List<Seckill> getSeckillList() {
        int offset = 0;
        int limit = 4;
        return seckillDao.queryAll(offset,limit);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
//        优化点：缓存优化
//        Seckill seckill = seckillDao.queryById(seckillId);
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            redisDao.putSeckill(seckill);
//            return new Exposer(false,seckillId);
        }
        long now = (new Date()).getTime();
        long startTime = seckill.getStartTime().getTime();
        long endTime = seckill.getEndTime().getTime();

        if(now < startTime || now > endTime){
            return new Exposer(false,seckillId,now,startTime,endTime);
        }

        String md5 = this.getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId){
        String base = seckillId + "/" + this.slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    @Transactional
    /*
    * 使用注解处理事务的优点
    * 1 明确事务编程风格
    * 2 事务处理时间短，不要穿插其他网络操作（若需要，剥离到事务外面）
    * 3 不是所有方法都需要事务处理
    * */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        try{
            if(md5 == null || !md5.equals(this.getMd5(seckillId))){
                throw new SeckillException("seckill data rewrite");
            }
//        执行秒杀逻辑 减库存 记录购买数据
            int reduce = seckillDao.reduceNumber(seckillId,new Date());
            if(reduce <= 0){
//            秒杀结束，没有插入数据
                throw new SeckillCloseException("seckill is closed");
            }else{
//            记录购买数据
                int insertSuccess = successKilledDao.insetSuccessKilled(seckillId,userPhone);
                if(insertSuccess <= 0){
                    throw new RepeatKillException("seckill repeated");
                }else{
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillState.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseException e1){
            throw e1;
        } catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error : " + e.getMessage());
        }

    }
}
