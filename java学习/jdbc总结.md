1）加载驱动
Class.forName("com.mysql.jdbc.Driver")
2）获取连接
3）获得PreparedStatement对象 ，或者获得 callableStatement（调用存储过程，分为有入参，有出参，有inout）
PreparedStatement preparedstatement = con.prepareStatement(String sql)
ResultSet rs = preparedstatement.execute()

CallableStatement	callablestatement = con.prepareCall("call procedure string")
callablestatement.execute()
ResultSet rs = callablestatement.getResultSet()

设计：
一个表对应一个操作类，一个存储类（bean）


事物：
1、要取消掉JDBC的自动提交：void con.setAutoCommit(boolean autoCommit)  // false

2、执行各个SQL语句，加入到批处理之中

3、如果所有语句执行成功，则提交事务 commit()；如果出现了错误，则回滚：rollback()

示例：
```
try {  
    Class.forName("com.mysql.jdbc.Driver");  
    conn = DriverManager.getConnection(URL, USER, PASSWD);  

    conn.setAutoCommit(false); // 自动提交设置为false  

    // 执行更新操作  
    pstmtupdate = conn.prepareStatement(updatesql);  
    pstmtupdate.executeUpdate();  

    // 执行查找操作  
    pstmtquery = conn.prepareStatement(querysql);  
    pstmtquery.executeQuery();  

    conn.commit();  
    conn.setAutoCommit(true);  

    pstmtupdate.close();  
    pstmtquery.close();  
    conn.close();  
} catch (Exception e) {  
    try {  
        conn.rollback();  
    } catch (SQLException e1) {}  
    e.printStackTrace();  
} 
```

连接池：
dbcp
c3p0

升级替代库：
hibernate
myBatis
