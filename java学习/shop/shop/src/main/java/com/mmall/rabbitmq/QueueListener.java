package com.mmall.rabbitmq;

/**
 * Created by daojia on 2018-9-27.
 */

import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

public class QueueListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        System.out.println("receive msg : " + new String(body));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费

    }

}

