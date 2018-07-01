package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

import java.util.List;

public interface ICartService {

    ServerResponse<CartVo> add(Integer userId,Integer count,Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> delete(Integer userId,List<Integer> productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId,Integer checked);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer checked,Integer productId);

    ServerResponse<Integer> countAllNumber(Integer userId);
}
