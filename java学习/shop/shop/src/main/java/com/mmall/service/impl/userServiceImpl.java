package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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

        // 密码登录md5
        User user = userMapper.selectLogin(username,MD5Util.MD5EncodeUtf8(password));
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword("");
        return ServerResponse.createBySuccess(user,"登录成功");
    }

    @Override
    public ServerResponse logout(){
        return ServerResponse.createBySuccessMessage("退出登录");
    }

    @Override
    public ServerResponse<User> register(User user){
        ServerResponse check_valid_result = this.checkValid(user.getUsername(),"username");
        if(!check_valid_result.isSuccess()){
            return check_valid_result;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        // md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int result_count = userMapper.insert(user);
        if(result_count == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.createBySuccess(user,"注册成功");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        // 这里用isNotBlank 而不是 isNotEmpty，要清楚区别
        if(StringUtils.isNotBlank(type)){
            if(Const.USER_NAME.equals(type)){
                int result_count = userMapper.checkUserName(str);
                if(result_count > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }

                return ServerResponse.createBySuccessMessage("用户名校验通过");
            }else{
                return ServerResponse.createByErrorMessage("参数错误");
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
    }
}
