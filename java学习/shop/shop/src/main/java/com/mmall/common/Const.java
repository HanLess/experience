package com.mmall.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String USER_NAME = "username";

    public interface Cart{
        int CHECHED = 1;
        int UN_CHECKED = 0;
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
}
