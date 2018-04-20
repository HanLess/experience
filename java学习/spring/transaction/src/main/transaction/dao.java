package transaction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by daojia on 2018-4-19.
 */
@Repository
public class dao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void TemplateAdd(String name,int age,double money){
        String sql = "insert into group_one (name,age,money) values (?,?,?)";
        jdbcTemplate.update(sql,name,age,money);
    }

    public void outMoney(String name,double moneyNumber){
        String sql = "update group_one set money = money - ? where name = ?";
        jdbcTemplate.update(sql,moneyNumber,name);
    }

    public void inMoney(String name,double moneyNumber){
        String sql = "update group_one set money = money + ? where name = ?";
        jdbcTemplate.update(sql,moneyNumber,name);
    }
}
