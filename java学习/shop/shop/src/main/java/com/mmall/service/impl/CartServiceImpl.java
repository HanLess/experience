package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.pojo.Cart;
import com.mmall.service.ICartService;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public ServerResponse add(Integer userId,Integer count, Integer productId) {
        Cart exist_cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(exist_cart != null){
            int add_result = cartMapper.addQuantity(userId , productId ,count);
            if(add_result == 0){
                return ServerResponse.createByErrorMessage("添加购物车失败");
            }
        }else{
            Cart cart = new Cart();
            cart.setChecked(Const.Cart.CHECHED);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setUserId(userId);

            int result = cartMapper.insert(cart);
            if(result == 0){
                return ServerResponse.createByErrorMessage("添加购物车失败");
            }
        }

        return null;
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.getCartByUserId(userId);
        List<CartProductVo> cartProductVos = new ArrayList<>();

        BigDecimal cartTotalPrice = new BigDecimal("0");
        cartVo.setCartTotalPrice(cartTotalPrice);

        return null;
    }
}
