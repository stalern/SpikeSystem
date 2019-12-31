package com.savannah.service;

import com.savannah.error.ReturnException;
import com.savannah.service.model.CategoryDTO;

import java.util.List;


/**
 * @author stalern
 * @date 2019/12/23~22:50
 */
public interface CategoryService {

    /**
     * 通过id拿到分类信息，包括分类下的商品
     * @param id 分类id
     * @return 分类信息
     */
    CategoryDTO getCategoryById(Integer id);

    /**
     * 列出所有分类
     * @return DTOList,没有itemIds
     */
    List<CategoryDTO> listCategory();

    /**
     * 创建分类
     * @param categoryDTO 包括分类商品
     * @return DTO
     */
    CategoryDTO createCategory(CategoryDTO categoryDTO) throws ReturnException;

    /**
     * 更新
     * @param categoryDTO 包括分类商品
     * @return VO
     */
    CategoryDTO updateCategory(CategoryDTO categoryDTO) throws ReturnException;

    /**
     * 删除分类
     * @param id 分类id
     */
    void deleteCategory(Integer id) throws ReturnException;
}
