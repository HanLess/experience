package com.mmall.controller.portal;


import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mmall.common.ServerResponse;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> user =  iUserService.login(username,password);
        if(user.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,user.getData());
        }
        return user;
    }
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        ServerResponse out = iUserService.logout();
        return out;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> register(User user){
        ServerResponse<User> new_user = iUserService.register(user);
        return new_user;
    }

    @RequestMapping(value = "checkValid",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }
}
