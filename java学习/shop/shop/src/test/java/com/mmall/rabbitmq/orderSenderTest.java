package com.mmall.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class orderSenderTest {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void sendOrder() {
        RabbitMqOrder rabbitMqOrder = new RabbitMqOrder(1,"one");
        try {
            orderSender.sendOrder(rabbitMqOrder);
        }catch (Exception e){
        }
    }
}