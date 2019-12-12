package com.savannah.dao;

import com.savannah.dataobject.ItemStockDO;

/**
 * @author stalern
 * @date 2019年12月9日17:21:00
 */
public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);
}