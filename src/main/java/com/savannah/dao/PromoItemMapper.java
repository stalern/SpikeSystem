package com.savannah.dao;

import com.savannah.dataobject.PromoItemDO;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:24:52
 */
@Repository
public interface PromoItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoItemDO record);

    int insertSelective(PromoItemDO record);

    PromoItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoItemDO record);

    int updateByPrimaryKey(PromoItemDO record);

    /**
     * 通过商品主键获得库存（当活动时间在当前时间内）
     * @param itemId 商品主键
     * @return 库存
     */
    PromoItemDO selectByItemId(Integer itemId);
}