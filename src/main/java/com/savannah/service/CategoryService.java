package com.savannah.service;

import com.savannah.service.model.CategoryDTO;


/**
 * @author stalern
 * @date 2019/12/23~22:50
 */
public interface CategoryService {

    /**
     * 通过id拿到分类信息
     * @param id 分类id
     * @return 分类信息
     */
    CategoryDTO getCategoryById(Integer id);
}
