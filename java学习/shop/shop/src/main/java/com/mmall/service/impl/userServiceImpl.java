package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Service("iUserService")
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

    @Override
    public ServerResponse<String> forgetPassword(String username) {
        if(userMapper.checkUserName(username) < 1){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.getQuestion(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createBySuccessMessage("获取登录问题失败");
    }

    @Override
    public ServerResponse<String> checkQuestion(String username, String question, String answer) {
        int result = userMapper.checkQuestion(username,question,answer);
        if(result > 0){
            // 生成并存储临时token，返回前端，之后的重置密码会进行比对
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.PREFIX + username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken,"答案正确！");
        }
        return ServerResponse.createByErrorMessage("答案错误");
    }

    @Override
    public ServerResponse forgetResetPassword(String username, String password, String token) {
        // 校验临时token
        if(!StringUtils.isNotBlank(token)){
            ServerResponse.createByErrorMessage("临时token为空");
        }
        if(userMapper.checkUserName(username) < 1){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String temp_token = TokenCache.getValue(TokenCache.PREFIX + username);
        if(!StringUtils.equals(token,temp_token)){
            return ServerResponse.createByErrorMessage("临时token失效");
        }

        int result = userMapper.forgetResetPassword(username,MD5Util.MD5EncodeUtf8(password));
        if(result > 0){
            return ServerResponse.createBySuccessMessage("重置密码成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if(user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }

        return ServerResponse.createByError();
    }
}
