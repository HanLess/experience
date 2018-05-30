-- 执行秒杀
delimiter //
CREATE PROCEDURE execute(
IN seckillId BIGINT,IN killTime DATE ,IN userPhone BIGINT, OUT result INT
)
BEGIN
  START TRANSACTION;
  insert ignore into success_killed (
      seckill_id,user_phone
  ) values (
      seckillId,userPhone
  );

  IF (row_count() <= 0) THEN
    ROLLBACK ;
    SET result = 1;
  ELSE
    update
      seckill
    set
      number = number - 1
    where seckill_id = seckillId
          and start_time <= killTime
    and end_time >= killTime
    and number > 0;

    IF (row_count() <= 0) THEN
      ROLLBACK ;
      SET result = 2;
    ELSE
      COMMIT ;
      SET result = 0;
    END IF;
  END IF;
END;//
delimiter ;