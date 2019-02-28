package com.leoren.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.leoren.mmall.common.Const;
import com.leoren.mmall.common.ResponseCode;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.dao.CartMapper;
import com.leoren.mmall.dao.CategoryMapper;
import com.leoren.mmall.dao.ProductMapper;
import com.leoren.mmall.pojo.Cart;
import com.leoren.mmall.pojo.Product;
import com.leoren.mmall.service.ICartService;
import com.leoren.mmall.util.BigDecimalUtil;
import com.leoren.mmall.util.PropertiesUtil;
import com.leoren.mmall.vo.CartProductVo;
import com.leoren.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.Severity;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CartServiceImpl
 * @Auther Leoren
 * @Date 2019/2/27 9:15
 * @Desc : 购物车Service 的实现类
 * @Version v1.0
 **/

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 购物车增加
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count){
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart == null){
            //这个产品不再购物车里  需要新增
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);

            cartMapper.insert(cartItem);
        }else {
            //这个商品已经在购物车 数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 更新购物车方法
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count){
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        CartVo cartVo = this.getCartVoLimit(userId);
        return this.list(userId);
    }

    /**
     * 购物车中删除产品
     * @param userId
     * @param productIds
     * @return
     */
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productList);
        CartVo cartVo = this.getCartVoLimit(userId);
        return this.list(userId);
    }

    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 全选或全反选
     * @param userId
     * @param checked
     * @return
     */
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked, Integer productId){
        cartMapper.checkedOrUncheckedProduct(userId, checked, productId);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");
        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                System.out.println("===================================="+cartItem.getProductId());

                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
//                    System.out.println(cartProductVo);
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //勾选
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                    //cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),0.0);
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }


    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
