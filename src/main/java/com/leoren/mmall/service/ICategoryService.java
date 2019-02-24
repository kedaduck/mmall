package com.leoren.mmall.service;

import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.Category;

import java.util.List;

/**
 * @ClassName ICategoryService
 * @Auther Leoren
 * @Date 2019/2/23 19:40
 * @Desc : 商品管理的Service
 * @Version v1.0
 **/
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse selectCategoryAndChildrenById(Integer categoryId);

}
