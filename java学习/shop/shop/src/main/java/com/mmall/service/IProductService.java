package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;

import java.util.List;

public interface IProductService {

    ServerResponse<Product> addOrUpdateProduct(Product product);

    ServerResponse<Integer> changeProductStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getList(Integer pageNumber, Integer pageSize);

    ServerResponse<PageInfo> search(String productName,Integer productId,Integer pageNumber, Integer pageSize);

    ServerResponse<ProductDetailVo> getDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeyWordCategory(String name,Integer categoryId,Integer pageNumber,Integer pageSize,String orderBy);
}
