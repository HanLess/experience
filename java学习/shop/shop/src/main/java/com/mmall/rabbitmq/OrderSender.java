package com.mmall.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.Correlation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(RabbitMqOrder order) throws Exception {

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getId().toString());

        rabbitTemplate.convertAndSend("order-exchange","order.abcd",order,correlationData);
    }
}
