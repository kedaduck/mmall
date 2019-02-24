package com.leoren.mmall.service;

import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.Product;
import com.leoren.mmall.vo.ProductDetailVo;

/**
 * @ClassName IProduct
 * @Auther Leoren
 * @Date 2019/2/24 10:56
 * @Desc : 商品管理的Service
 * @Version v1.0
 **/
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

}
