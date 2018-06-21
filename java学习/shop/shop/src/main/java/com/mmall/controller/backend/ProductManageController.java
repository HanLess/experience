package com.mmall.controller.backend;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "addOrUpdateProduct",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Product> addOrUpdateProduct(HttpSession session,Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("需要管理员账号");
        }

        return iProductService.addOrUpdateProduct(product);
    }

    @RequestMapping(value = "changeProductStatus",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Integer> changeProductStatus(HttpSession session,Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("需要管理员账号");
        }

        return iProductService.changeProductStatus(productId,status);
    }

    @RequestMapping(value = "getProductDetail",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> getProductDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("需要管理员账号");
        }

        return iProductService.getProductDetail(productId);
    }
}
