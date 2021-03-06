package com.mmall.rabbitmq;

/**
 * Created by daojia on 2018-9-27.
 */

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.UUID;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;

public class RmqProducer implements ConfirmCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /* (non-Javadoc)
     * @see com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
     */
    public void sendMessage(String content,String key) {
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend("order-exchange-2", key,content,correlationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack,String msg) {
        System.out.println(" 回调id:" + correlationData+"ack:"+ack + "----msg :" + msg);
    }
}





















