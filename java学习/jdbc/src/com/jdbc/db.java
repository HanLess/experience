package com.jdbc;

import java.sql.*;

public class db {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/mooc?characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String password = "111111";

    private static Connection con = null;

    static {
        try {
            // 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection(){
        try {
            con = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
