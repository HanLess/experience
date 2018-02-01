package dao;

import java.sql.*;
import com.jdbc.db;

import java.sql.Date;
import java.util.*;
import Model.goddess;

public class goddessDao {
    public void addGoddess(goddess gs){
        try{
            Connection con = db.getConnection();
            String sql = "insert into imooc_goddess " +
                    "(user_name,sex,age,birthday,email,mobile,create_user,create_date,update_user,update_date,isdel) " +
                    "values " +
                    "(?,?,?,?,?,?,?,current_date(),?,current_date(),?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1,gs.getUser_name());
            preparedStatement.setInt(2,gs.getSex());
            preparedStatement.setInt(3,gs.getAge());
            preparedStatement.setDate(4,new Date(gs.getBirthday().getTime()));
            preparedStatement.setString(5,gs.getEmail());
            preparedStatement.setString(6,gs.getMobile());
            preparedStatement.setString(7,gs.getCreate_user());
            preparedStatement.setString(8,gs.getUpdate_user());
            preparedStatement.setInt(9,gs.getIsdel());

            preparedStatement.execute();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delGoddess(Integer id){
        Connection con = db.getConnection();
        try {
            String sql = "delete from imooc_goddess where id=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateGoddess(String old_name,String new_name){
        try{
            Connection con = db.getConnection();
            String sql = "update imooc_goddess " +
                    "set user_name=? " +
                    "where user_name=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1,new_name);
            preparedStatement.setString(2,old_name);

            preparedStatement.execute();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<goddess> query(){
        Connection con = db.getConnection();
        List<goddess> goddessList = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select user_name,age from imooc_goddess");

            goddess g = null;

            while (rs.next()){
                g = new goddess();
                g.setUser_name(rs.getString("user_name"));
                g.setAge(rs.getInt("age"));

                goddessList.add(g);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goddessList;
    }

    public goddess get(Integer id){
        goddess gs = null;
        Connection con = db.getConnection();
        try {
            String sql = "select user_name,age from imooc_goddess where id=?";

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                gs = new goddess();
                gs.setUser_name(rs.getString("user_name"));
                gs.setAge(rs.getInt("age"));
            }
            return gs;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
//    模糊查询，与匹配符 % _ [] [^]
    public goddess getByPro(Map<String,Object> map){
        Connection con = db.getConnection();
        goddess gd = null;
        try{
            StringBuilder sql = new StringBuilder();
            sql.append("select user_name,age from imooc_goddess where 1=1 ");

//            for(Map<String,Object> map:list){
                sql.append("and " + map.get("name") + " " + map.get("rela") + " " + map.get("value"));
//            }

            PreparedStatement preparedStatement = con.prepareStatement(sql.toString());
            ResultSet rs = preparedStatement.executeQuery();
            gd = new goddess();
            while (rs.next()){
                gd.setUser_name(rs.getString("user_name"));
                gd.setAge(rs.getInt("age"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return gd;
    }
}
