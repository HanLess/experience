package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iShippingService")
public class ShippingImpl implements IShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse<Shipping> add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int result = shippingMapper.insert(shipping);
        if(result == 0){
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccess(shipping,"添加成功");
    }

    @Override
    public ServerResponse delete(Integer userId,Integer shippingId) {
        int result = shippingMapper.deleteByPrimaryKeyUserId(userId,shippingId);
        if(result == 0){
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public ServerResponse<Shipping> update(Integer userId,Shipping shipping) {
        Integer shippingId = shipping.getId();
        if(shippingId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        shipping.setUserId(userId);
        int result = shippingMapper.updateShipping(shipping);
        if(result == 0){
            return ServerResponse.createByErrorMessage("修改失败");
        }

        return ServerResponse.createBySuccess(shipping,"修改成功");
    }

    @Override
    public ServerResponse<Shipping> getDetail(Integer userId, Integer shippingId) {
        if(shippingId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Shipping shipping = shippingMapper.selectByPrimaryKeyUserId(userId,shippingId);
        return ServerResponse.createBySuccess(shipping);
    }

    @Override
    public ServerResponse<PageInfo> getList(Integer userId,Integer pageSize,Integer pageNumber) {
        PageHelper.startPage(pageNumber,pageSize);

        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageResult = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageResult);
    }
}
