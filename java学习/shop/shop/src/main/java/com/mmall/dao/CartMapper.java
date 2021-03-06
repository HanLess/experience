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

    int deleteByUserIdAndProductIds(@Param("userId") Integer userId,@Param("productIds") List<Integer> productIds);

    int selectOrUnSelectAll(@Param("userId") Integer userId,@Param("checked") Integer checked);

    int selectOrUnSelect(@Param("userId") Integer userId,@Param("checked") Integer checked,@Param("productId") Integer productId);

    int countAllNumber(Integer userId);

    List<Cart> selectByUserIdChecked(Integer userId);

    int emptyCart(Integer userId);
}