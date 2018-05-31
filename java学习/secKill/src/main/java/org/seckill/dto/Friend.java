package org.seckill.dto;

/**
 * Created by daojia on 2018-5-31.
 */
public class Friend {
    private String friend;

    public String getName() {
        return friend;
    }

    public void setName(String name) {
        this.friend = name;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friend" + friend + '\'' +
                '}';
    }
}
