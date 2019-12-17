package com.savannah.dao;

import com.savannah.dataobject.ItemInfoDO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:20:36
 */
@Repository
public interface ItemInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemInfoDO record);

    int insertSelective(ItemInfoDO record);

    /**
     * 通过主键获得商品信息
     * @param id 主键
     * @return 商品信息(不包括库存，参加活动)
     */
    ItemInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemInfoDO record);

    int updateByPrimaryKey(ItemInfoDO record);

    /**
     * 增加销量，这里其实可以用mybatis自带的updateSelective
     * @param itemId 商品id
     * @param amount 数量
     * @return 主键
     */
    @Update("update item_info set sales = #{amount} + sales where item_id = itemId")
    int increaseSales(Integer itemId, Integer amount);
}