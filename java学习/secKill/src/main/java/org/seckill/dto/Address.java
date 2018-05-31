package org.seckill.dto;

/**
 * Created by daojia on 2018-5-31.
 */
public class Address {
    private String code;
    private String address;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Address [code=" + code + ", address=" + address + "]";
    }
}
