package com.mmall.rabbitmq;

/**
 * Created by daojia on 2018-9-27.
 */

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.DirectExchange;

public class MyBinding {

    private Queue queue;
    private TopicExchange exchange;
    private String routingkey;

    private MyBinding(Queue queue,TopicExchange exchange,String routingkey){
        this.queue = queue;
        this.exchange = exchange;
        this.routingkey = routingkey;
    }

    public Binding createBinding(){
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }
}

