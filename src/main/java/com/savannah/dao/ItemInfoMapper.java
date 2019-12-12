package com.savannah.dao;

import com.savannah.dataobject.ItemInfoDO;

/**
 * @author stalern
 * @date 2019年12月9日17:20:36
 */
public interface ItemInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemInfoDO record);

    int insertSelective(ItemInfoDO record);

    ItemInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemInfoDO record);

    int updateByPrimaryKey(ItemInfoDO record);
}