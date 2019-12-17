package com.savannah.dao;

import com.savannah.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:21:00
 */
@Repository
public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 通过商品id获得库存
     * @param itemId 商品id
     * @return 库存
     */
    ItemStockDO selectByItemId(Integer itemId);

    /**
     * 减少库存
     * @param itemId 商品id
     * @param amount 数量
     * @return 主键
     */
    @Update("update item_stock set stock = stock - #{amount} where item_id = #{itemId} and stock >= #{amount}")
    int decreaseStock(Integer itemId, Integer amount);
}