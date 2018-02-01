package action;

import java.util.*;

import dao.goddessDao;
import Model.goddess;

public class actionGoddess {

    public static void main(String... args){
        goddess gd = new goddess();
        gd.setUser_name("哈哈");
        gd.setAge(3);
        gd.setBirthday(new Date());
        gd.setSex(1);
        gd.setEmail("xiaomi@163.com");
        gd.setMobile("13000000000");
        gd.setCreate_user("han");
        gd.setUpdate_user("han");

        goddessDao gdo = new goddessDao();

    }
}
