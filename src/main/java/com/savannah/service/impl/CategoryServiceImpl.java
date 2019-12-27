package com.savannah.service.impl;

import com.savannah.dao.CategoryInfoMapper;
import com.savannah.entity.CategoryInfoDO;
import com.savannah.service.CategoryService;
import com.savannah.service.model.CategoryDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author stalern
 * @date 2019/12/24~08:45
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;
    @Override
    public CategoryDTO getCategoryById(Integer id) {
        return convertDtoFromDo(categoryInfoMapper.selectByPrimaryKey(id));
    }

    private CategoryDTO convertDtoFromDo(CategoryInfoDO categoryInfoDO) {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(categoryInfoDO,categoryDTO);
        return categoryDTO;
    }
}
