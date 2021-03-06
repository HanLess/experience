package com.mmall.rabbitmq;

/**
 * Created by daojia on 2018-9-27.
 */

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("receive msg : " + new String(body));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
    }

    @RabbitListener(queues = "order-queue-2")
    public void processMessage(@Payload String body,Message message, Channel channel){
        System.out.println("body："+body);
//        System.out.println("token："+token);

        byte[] messageBody = message.getBody();
        System.out.println("receive msg : " + new String(messageBody));

//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
    }

}

