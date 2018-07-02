package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> add(Integer userId,Integer count, Integer productId) {
        if(userId == null || productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart exist_cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(exist_cart != null){
            int add_result = cartMapper.addQuantity(userId , productId ,count);
            if(add_result == 0){
                return ServerResponse.createByErrorMessage("添加购物车失败");
            }
        }else{
            Cart cart = new Cart();
            cart.setChecked(Const.Cart.CHECKED);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setUserId(userId);

            int result = cartMapper.insert(cart);
            if(result == 0){
                return ServerResponse.createByErrorMessage("添加购物车失败");
            }
        }
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId){
        if(userId == null || productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart exist_cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(exist_cart != null){
            exist_cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(exist_cart);
            CartVo cartVo = this.getCartVoLimit(userId);
            return ServerResponse.createBySuccess(cartVo);
        }
        return ServerResponse.createByErrorMessage("更新购物车失败");
    }

    @Override
    public ServerResponse<CartVo> delete(Integer userId, List<Integer> productIds) {
        if(userId == null || productIds == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        int result = cartMapper.deleteByUserIdAndProductIds(userId,productIds);
        if(result == 0){
            return ServerResponse.createByErrorMessage("删除失败");
        }

        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelectAll(Integer userId,Integer checked) {
        int result = 0;
        if(checked == Const.Cart.CHECKED){
            result = cartMapper.selectOrUnSelectAll(userId,checked);
        }else if(checked == Const.Cart.UN_CHECKED){
            result = cartMapper.selectOrUnSelectAll(userId,checked);
        }else{
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if(result > 0){
            CartVo cartVo = getCartVoLimit(userId);
            return ServerResponse.createBySuccess(cartVo);
        }

        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId) {
        int result = 0;
        if(checked == Const.Cart.CHECKED){
            result = cartMapper.selectOrUnSelect(userId,checked,productId);
        }else if(checked == Const.Cart.UN_CHECKED){
            result = cartMapper.selectOrUnSelect(userId,checked,productId);
        }else{
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        if(result > 0){
            CartVo cartVo = getCartVoLimit(userId);
            return ServerResponse.createBySuccess(cartVo);
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse<Integer> countAllNumber(Integer userId) {
        Integer num = cartMapper.countAllNumber(userId);
        return ServerResponse.createBySuccess(num);
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.getCartByUserId(userId);
        List<CartProductVo> cartProductVos = new ArrayList<>();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        for(Cart cart : cartList){
            CartProductVo cartProductVo = new CartProductVo();
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());

                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cart.getProductId());
            if(product != null){
                cartProductVo.setProductName(product.getName());
                cartProductVo.setSubtitle(product.getSubtitle());
                cartProductVo.setMainImage(product.getMainImage());
                cartProductVo.setPrice(product.getPrice());
                cartProductVo.setStatus(product.getStatus());
                cartProductVo.setStock(product.getStock());

                // 判断库存
                int buyLimitCount = 0;
                if(product.getStock() >= cart.getQuantity()){
                    buyLimitCount = cart.getQuantity();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                }else{
                    buyLimitCount = product.getStock();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                    // 更新有效库存
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setId(cart.getId());
                    cartForQuantity.setQuantity(buyLimitCount);
                    cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                }
                cartProductVo.setQuantity(buyLimitCount);
                cartProductVo.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue() ,buyLimitCount));
                cartProductVo.setChecked(cart.getChecked());

                // 如果已经勾选，增加到购物车整体的总价中
                if(cart.getChecked() == Const.Cart.CHECKED){
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getTotalPrice());
                }

                cartProductVos.add(cartProductVo);
            }
        }
        cartVo.setCartProductVos(cartProductVos);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        boolean status = true;
        int result = cartMapper.getAllCheckStatusByUserId(userId);
        if(result > 0){
            status = false;
        }

        return status;
    }
}
