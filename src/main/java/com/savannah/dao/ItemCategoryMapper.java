package com.savannah.dao;

import com.savannah.dataobject.ItemCategoryDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemCategoryDO record);

    int insertSelective(ItemCategoryDO record);

    ItemCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemCategoryDO record);

    int updateByPrimaryKey(ItemCategoryDO record);

    /**
     * 列出一个商品所有的分类
     * @param itemId 商品id
     * @return 分类列表
     */
    List<ItemCategoryDO> selectByItemId(Integer itemId);
}