package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;

    @Override
    public ServerResponse<Product> addOrUpdateProduct(Product product) {
        int result;
        if(product == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if(product.getId() != null){
            result = productMapper.updateByPrimaryKey(product);
        }else{
            result = productMapper.insert(product);
        }

        if(result == 0){
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccess(product,"添加成功");
    }
    @Override
    public ServerResponse<Integer> changeProductStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }

        int result = productMapper.changeProductStatus(productId,status);
        if(result == 0){
            return ServerResponse.createByErrorMessage("修改状态失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createBySuccessMessage("产品已删除");
        }

        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo,"查询成功");
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
//        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
//        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        productDetailVo.setCreateTime(sdf.format(product.getCreateTime()));
        productDetailVo.setUpdateTime(sdf.format(product.getUpdateTime()));

        return productDetailVo;
    }

    @Override
    public ServerResponse<PageInfo> getList(Integer pageNumber,Integer pageSize) {
        /*
        * 1 pagestart -> start
        * 2 填充自己的sql查询逻辑
        * 3 pageHelper收尾
        * */
        PageHelper.startPage(pageNumber,pageSize);
        List<Product> products = productMapper.getList();
        List<ProductListVo> productListVos = null;
        for(Product product : products){
            productListVos.add(assembleProductListVo(product));
        }

        PageInfo pageResult = new PageInfo();
        pageResult.setList(productListVos);

        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();

        productListVo.setId(product.getId());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setName(product.getName());
        productListVo.setStatus(product.getStatus());

        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        return productListVo;
    }

    @Override
    public ServerResponse<PageInfo> search(String productName,Integer productId,Integer pageNumber, Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().
                                            append("%").
                                            append(productName).
                                            append("%").toString();
        }
        List<Product> products = productMapper.search(productName,productId);
        List<ProductListVo> productListVos = null;

        for(Product product : products){
            productListVos.add(assembleProductListVo(product));
        }

        PageInfo pageResult = new PageInfo();
        pageResult.setList(productListVos);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<ProductDetailVo> getDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createBySuccessMessage("产品已删除");
        }

        if(product.getStatus() != Const.ProductStatusCode.ON_SALE.getCode()){
            return ServerResponse.createBySuccessMessage("商品已下架");
        }

        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo,"查询成功");
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeyWordCategory(String name,Integer categoryId,Integer pageNumber,Integer pageSize,String orderBy){
        if(StringUtils.isBlank(name) && categoryId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categories = null;

        if(categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(name)){
                PageHelper.startPage(pageNumber,pageSize);
                PageInfo pageInfo = new PageInfo(new ArrayList());
                return ServerResponse.createBySuccess(pageInfo);
            }
            categories = iCategoryService.getCategoryAndDeepChildrenCategory(categoryId).getData();
        }
        if(StringUtils.isNotBlank(name)){
            name = new StringBuilder().append("%").append(name).append("%").toString();
        }
        PageHelper.startPage(pageNumber,pageSize);
        // 排序
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        List<Product> products = productMapper.selectByNameAndCategoryIds(name,categories);
        List<ProductListVo> productListVos = null;
        for(Product product:products){
            productListVos.add(assembleProductListVo(product));
        }

        PageInfo pageResult = new PageInfo();
        pageResult.setList(productListVos);

        return ServerResponse.createBySuccess(pageResult);
    }
}
