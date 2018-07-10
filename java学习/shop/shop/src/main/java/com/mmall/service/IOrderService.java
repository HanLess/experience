package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

public interface IOrderService {

    ServerResponse<Map<String,String>> pay(Long orderNo,Integer userId,String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse<String> queryPay(Integer userId,Long orderNo);


}
