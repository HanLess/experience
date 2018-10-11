package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface TestMapper {

    @Select({
        "select id, category_id, name, subtitle, main_image, sub_images, detail, price, stock, status,create_time, update_time",
        "from mmall_product"
    })
    List<Product> getList();
}
