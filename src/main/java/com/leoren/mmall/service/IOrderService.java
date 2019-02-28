package com.leoren.mmall.service;

import com.github.pagehelper.PageInfo;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.vo.OrderVo;

import java.util.Map;

/**
 * @ClassName IOrderService
 * @Auther Leoren
 * @Date 2019/2/28 15:04
 * @Desc :
 * @Version v1.0
 **/
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse<String> cancel(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


    //以下是管理员

    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
