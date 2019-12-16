package com.savannah.dao;

import com.savannah.dataobject.ItemCategoryDO;

public interface ItemCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemCategoryDO record);

    int insertSelective(ItemCategoryDO record);

    ItemCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemCategoryDO record);

    int updateByPrimaryKey(ItemCategoryDO record);
}