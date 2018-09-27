package com.mmall.controller.rabbitmq;

import com.mmall.rabbitmq.RmqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by daojia on 2018-9-27.
 */


@Controller
public class one {

    @Autowired
    private RmqProducer rmqProducer;

    @RequestMapping(value = "send")
    @ResponseBody
    public String sendMessage(){

        try {
            rmqProducer.sendMessage("this is test");
        }catch (Exception e){

        }

        return null;
    }
}
