package com.savannah.dao;

import com.savannah.entity.ItemStockDO;
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

    /**
     * 插入商品库存
     * @param record 商品库存
     * @return 主键
     */
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
     * @return 影响的条目数，如果执行失败则为0
     */
    @Update("update item_stock set stock = stock - #{amount} where item_id = #{itemId} and stock >= #{amount}")
    int decreaseStock(Integer itemId, Integer amount);

    /**
     * 更新商品库存
     * @param itemStockDO 商品库存
     */
    void updateByItemIdSelective(ItemStockDO itemStockDO);

    /**
     * 通过商品id删除商品库存
     * @param id itemId
     */
    void deleteByItemId(Integer id);
}