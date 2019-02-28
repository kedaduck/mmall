package com.leoren.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.service.IProductService;
import com.leoren.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ProductController
 * @Auther Leoren
 * @Date 2019/2/26 17:47
 * @Desc : 前台产品的controller
 * @Version v1.0
 **/

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    /**
     * 用户端的detail 详情展示
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){

        return iProductService.getProductDetail(productId);
    }

    /**
     * 根据关键字和category查找商品
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy ){

        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
