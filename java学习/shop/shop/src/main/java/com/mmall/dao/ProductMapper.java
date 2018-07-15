package com.mmall.dao;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int changeProductStatus(@Param("productId") Integer productId,@Param("status") Integer status);

    List<Product> getList();

    List<Product> search(@Param("productName") String ProductName,@Param("productId") Integer productId);

    List<Product> selectByNameAndCategoryIds(@Param("name") String name,@Param("categoryIds") List<Integer> categoryIds);

    int reduceStock(@Param("productId") Integer productId,@Param("quantity") Integer quantity);
}