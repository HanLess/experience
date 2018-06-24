package com.mmall.controller.backend;


import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

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

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> getList(HttpSession session,
                                            @RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
                                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
                                                         ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("需要管理员账号");
        }
        return iProductService.getList(pageNumber,pageSize);
    }

    @RequestMapping(value = "search",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> search(HttpSession session,
                                            @RequestParam(value = "productName",required = false) String productName,
                                            @RequestParam(value = "productId",required = false) Integer productId,
                                            @RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
                                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize
    ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("需要管理员账号");
        }
        return iProductService.search(productName,productId,pageNumber,pageSize);
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> upload(MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map fileMap = new HashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }
}
