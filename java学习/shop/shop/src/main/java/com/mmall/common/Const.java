package com.mmall.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String USER_NAME = "username";

    public interface Cart{
        int CHECKED = 1;
        int UN_CHECKED = 0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = new HashSet<>(Arrays.asList("price_desc","price_asc"));
    }

    public interface Role{
        public static final int ROLE_CUSTOMER = 0; // 普通用户
        public static final int ROLE_ADMIN = 1;     // 管理员
    }

    public enum ProductStatusCode{
        ON_SALE(1,"在售");

        private int code;
        private String msg;

        ProductStatusCode(Integer code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public String getMsg(){
            return this.msg;
        }

        public int getCode(){
            return this.code;
        }
    }

    public enum OrderStatus{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");


        OrderStatus(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        private int code;
        private String msg;

        public int getCode(){
            return this.code;
        }

        public String getMsg(){
            return this.msg;
        }
    }

    public interface AlipayCallback{
        String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum payPlatForm{
        ALIPAY(1,"支付宝");

        payPlatForm(int code,String name){
            this.code = code;
            this.name = name;
        }

        private int code;
        private String name;
        public int getCode(){
            return this.code;
        }

        public String getName(){
            return this.name;
        }
    }
}
