package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Shipping> add(HttpSession session,Shipping shipping){
        Integer userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();
        return iShippingService.add(userId,shipping);
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(HttpSession session,Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iShippingService.delete(user.getId(),shippingId);
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Shipping> update(HttpSession session,Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iShippingService.update(user.getId(),shipping);
    }

    @RequestMapping(value = "getDetail",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Shipping> getDetail(HttpSession session,Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iShippingService.getDetail(user.getId(),shippingId);
    }

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> getList(HttpSession session, @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize, @RequestParam(value = "pageSize" ,defaultValue = "1")  Integer pageNumber){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iShippingService.getList(user.getId(),pageSize,pageNumber);
    }
}
