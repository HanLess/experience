package com.mmall.rabbitmq;

/**
 * Created by daojia on 2018-9-27.
 */

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

public class MyBinding {

    private Queue queue;
    private DirectExchange exchange;
    private String routingkey;

    private MyBinding(Queue queue,DirectExchange exchange,String routingkey){
        this.queue = queue;
        this.exchange = exchange;
        this.routingkey = routingkey;
    }

    public Binding createBinding(){
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }
}

