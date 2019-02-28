package com.leoren.mmall.service;

import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.vo.CartVo;

/**
 * @ClassName ICartService
 * @Auther Leoren
 * @Date 2019/2/27 9:14
 * @Desc : 购物车的Service
 * @Version v1.0
 **/
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse selectOrUnSelect(Integer userId, Integer checked, Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
