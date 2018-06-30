package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdProductId(@Param(value = "userId") Integer userId,@Param("productId") Integer productId);

    int addQuantity(@Param(value = "userId") Integer userId,@Param("productId") Integer productId,@Param("addNumber") Integer addNumber);

    List<Cart> getCartByUserId(Integer userId);

    int getAllCheckStatusByUserId(Integer userId);

    int deleteByUserIdAndProductIds(@Param("userId") Integer userId,@Param("productId") List<Integer> productIds);
}