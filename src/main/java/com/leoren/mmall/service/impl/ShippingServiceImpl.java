package com.leoren.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.dao.ShippingMapper;
import com.leoren.mmall.pojo.Shipping;
import com.leoren.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ShippingServiceImpl
 * @Auther Leoren
 * @Date 2019/2/27 17:49
 * @Desc :
 * @Version v1.0
 **/

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 新增地址
     * @param userId
     * @param shipping
     * @return
     */
    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            System.out.println(shipping);
            return ServerResponse.createBySuccess("新建地址成功", result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");

    }

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse<String> delete(Integer userId, Integer shippingId){
        int resultCount = shippingMapper.deleteByShippingIdAndUserId(userId, shippingId);
        if(resultCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    /**
     * 更新地址
     * @param userId
     * @param shipping
     * @return
     */
    public ServerResponse update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    /**
     * 查询地址
     * @param userId
     * @param shippingId
     * @return
     */
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdAndUserId(userId, shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询该地址");
        }
        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    /**
     * 查询用户所哟地址
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
