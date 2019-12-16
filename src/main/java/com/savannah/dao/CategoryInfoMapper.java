package com.savannah.dao;

import com.savannah.dataobject.CategoryInfoDO;

public interface CategoryInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryInfoDO record);

    int insertSelective(CategoryInfoDO record);

    CategoryInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryInfoDO record);

    int updateByPrimaryKey(CategoryInfoDO record);
}