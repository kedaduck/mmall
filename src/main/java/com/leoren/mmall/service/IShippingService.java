package com.leoren.mmall.service;

import com.github.pagehelper.PageInfo;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.Shipping;

/**
 * @ClassName IShipping
 * @Auther Leoren
 * @Date 2019/2/27 17:49
 * @Desc :
 * @Version v1.0
 **/
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> delete(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
