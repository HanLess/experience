package transaction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by daojia on 2018-4-19.
 */
@Component
public class db {

    @Autowired
    private static ComboPooledDataSource ds;

//    static {
//        ds = new ComboPooledDataSource();
//
//        try {
//            ds.setDriverClass("com.mysql.jdbc.Driver");
//            ds.setJdbcUrl( "jdbc:mysql://localhost:3306/friends?useSSL=false&serverTimezone=UTC" );
//            ds.setUser("root");
//            ds.setPassword("111111");
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//    }

    public ComboPooledDataSource getDataSource(){
        return ds;
    }

    public Connection getConnect(){
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
