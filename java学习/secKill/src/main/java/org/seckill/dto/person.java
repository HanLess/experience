package org.seckill.dto;

/**
 * Created by daojia on 2018-5-14.
 */
public class person {
    private String name;
    private Long seckillId;

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", seckillId=" + seckillId +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }
}
