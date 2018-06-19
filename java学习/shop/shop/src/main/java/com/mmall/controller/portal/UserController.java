package com.mmall.controller.portal;


import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
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
    @RequestMapping(value = "logout",method = RequestMethod.POST)
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

    @RequestMapping(value = "checkValid",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess(user,"获取用户信息成功");
    }

    @RequestMapping(value = "forgetPassword",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPassword(String username){
        ServerResponse<String> question = iUserService.forgetPassword(username);
        return question;
    }

    @RequestMapping(value = "checkQuestion",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkQuestion(String username,String question,String answer){
        return iUserService.checkQuestion(username,question,answer);
    }

    @RequestMapping(value = "forgetResetPassword",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse forgetResetPassword(String username,String password,String token){
        return iUserService.forgetResetPassword(username,password,token);
    }

    @RequestMapping(value = "getInformation",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return ServerResponse.createBySuccess(user,"查询成功");
    }
}
