package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {

    ServerResponse<Product> addOrUpdateProduct(Product product);

    ServerResponse<Integer> changeProductStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
}
