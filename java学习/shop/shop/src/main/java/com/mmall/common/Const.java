package com.mmall.common;

public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String USER_NAME = "userName";

    public interface Role{
        public static final int ROLE_CUSTOMER = 0; // 普通用户
        public static final int ROLE_ADMIN = 1;     // 管理员
    }
}
