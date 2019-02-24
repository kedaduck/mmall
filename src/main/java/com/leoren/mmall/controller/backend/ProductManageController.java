package com.leoren.mmall.controller.backend;

import com.leoren.mmall.common.Const;
import com.leoren.mmall.common.ResponseCode;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.Product;
import com.leoren.mmall.pojo.User;
import com.leoren.mmall.service.IProductService;
import com.leoren.mmall.service.IUserService;
import com.leoren.mmall.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ProductManageController
 * @Auther Leoren
 * @Date 2019/2/24 10:51
 * @Desc :  商品管理Controller
 * @Version v1.0
 **/

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 保存或更新产品信息
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            //增加产品
            return iProductService.saveOrUpdateProduct(product);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            //增加产品
            return iProductService.setSaleStatus(productId, status);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 商品的详细信息
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            return iProductService.manageProductDetail(productId);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            //return iProductService.manageProductDetail(productId);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }



}
