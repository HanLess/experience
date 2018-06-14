package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultMapper = userMapper.checkUserName(username);
        if(resultMapper == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        // todo 密码登录md5
        User user = userMapper.selectLogin(username,password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword("");
        return ServerResponse.createBySuccess(user,"登录成功");
    }
}
