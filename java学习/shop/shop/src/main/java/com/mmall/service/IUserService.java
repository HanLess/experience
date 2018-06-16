package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import net.sf.jsqlparser.schema.Server;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse logout();

    ServerResponse<User> register(User user);

    ServerResponse checkValid(String str,String type);

    ServerResponse<String> forgetPassword(String username);

    ServerResponse<String> checkQuestion(String username,String question,String answer);

    ServerResponse forgetResetPassword(String username,String password,String token);

}
