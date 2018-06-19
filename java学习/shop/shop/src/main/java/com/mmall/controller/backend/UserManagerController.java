package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.service.IUserService;
import com.mmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/user/")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> result = iUserService.login(username,password);
        int role;
        if(result.isSuccess()){
            role = result.getData().getRole();
            if(role == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,result.getData());
                return result;
            }

            return ServerResponse.createByErrorMessage("不是管理员");
        }
        return result;
    }
}
