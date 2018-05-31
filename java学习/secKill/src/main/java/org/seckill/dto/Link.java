package org.seckill.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by daojia on 2018-5-31.
 */
public class Link {
    private List<Map<String,String>> address;
    public List<Map<String,String>> getAddress() {
        return address;
    }
    public void setAddress(List<Map<String,String>> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Link{" +
                ", address=" + address +
                '}';
    }
}
