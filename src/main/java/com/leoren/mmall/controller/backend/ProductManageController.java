package com.leoren.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.leoren.mmall.common.Const;
import com.leoren.mmall.common.ResponseCode;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.Product;
import com.leoren.mmall.pojo.User;
import com.leoren.mmall.service.IFileService;
import com.leoren.mmall.service.IProductService;
import com.leoren.mmall.service.IUserService;
import com.leoren.mmall.service.impl.ProductServiceImpl;
import com.leoren.mmall.util.PropertiesUtil;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private IFileService iFileService;

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

    /**
     * 查询产品List
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            return iProductService.getProductList(pageNum, pageSize);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 查找商品
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            System.out.println(productName);
            //管理员
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 上传文件
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，需要登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);

            return ServerResponse.createBySuccess(fileMap);
        }else {
            //非管理员
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 富文本模式上传文件
     * @param session
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){

        Map resultMap = Maps.newHashMap();

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success", false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        //富文本中对返回值有自己的要求，我们使用的是simditor 所以按照此要求进行返回
//        {
//            "success": true/false,
//            "msg": "error message", #optional
//            "file_path": "[real file path]"
//        }
        if(iUserService.checkAdminRole(user).isSuccess()){ //检查用户身份
            //管理员
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success", false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            resultMap.put("success", true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        }else {
            //非管理员
            resultMap.put("success", false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }







}
