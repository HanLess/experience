package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

public interface IShippingService {

    ServerResponse<Shipping> add(Integer userId,Shipping shipping);

    ServerResponse delete(Integer userId,Integer shippingId);

    ServerResponse<Shipping> update(Integer userId ,Shipping shipping);

    ServerResponse<Shipping> getDetail(Integer userId,Integer shippingId);

    ServerResponse<PageInfo> getList(Integer userId,Integer pageSize,Integer pageNumber);
}
