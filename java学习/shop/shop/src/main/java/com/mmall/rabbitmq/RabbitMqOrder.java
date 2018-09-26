package com.mmall.rabbitmq;

import java.io.Serializable;

public class RabbitMqOrder implements Serializable {
    private static final long serialVersionUID = 3603843892542422120L;

    private Integer id;
    private String message;

    public RabbitMqOrder(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RabbitMqOrder{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
