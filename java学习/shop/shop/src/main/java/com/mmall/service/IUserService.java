package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse logout();

    ServerResponse<User> register(User user);

    ServerResponse checkValid(String str,String type);
}
