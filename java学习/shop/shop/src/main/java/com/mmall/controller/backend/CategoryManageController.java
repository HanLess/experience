package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "addCategory",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, @RequestParam(value = "parentId",defaultValue = "0") int parentId,String name){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员账号");
        }

        return iCategoryService.addCategory(parentId,name,1);
    }

    @RequestMapping(value = "rename",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse rename(HttpSession session,int id,String name){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }

        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员账号");
        }

        return iCategoryService.rename(id,name);
    }

    @RequestMapping(value = "getChildrenParallelCategory",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员账号");
        }

        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping(value = "getCategoryAndDeepChildrenCategory",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if(!iUserService.checkAdminRole(user).isSuccess()){
            return ServerResponse.createByErrorMessage("非管理员账号");
        }

        return iCategoryService.getCategoryAndDeepChildrenCategory(categoryId);
    }
}
