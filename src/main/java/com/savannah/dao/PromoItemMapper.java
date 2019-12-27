package com.savannah.dao;

import com.savannah.entity.PromoItemDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stalern
 * @date 2019年12月9日17:24:52
 */
@Repository
public interface PromoItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoItemDO record);

    /**
     * 插入活动商品
     * @param record promoItem
     * @return 主键
     */
    int insertSelective(PromoItemDO record);

    PromoItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoItemDO record);

    int updateByPrimaryKey(PromoItemDO record);

    /**
     * 通过商品主键获得活动（当活动时间在当前时间内）
     * @param itemId 商品主键
     * @return 商品信息，因为商品只能参加一种活动，所以返回一定只有一个值
     */
    PromoItemDO selectByItemId(Integer itemId);

    /**
     * 通过活动id列出活动商品的表
     * @param promoId 活动id
     * @return list商品信息
     */
    List<PromoItemDO> listPromoItemByPromoId(Integer promoId);

    /**
     * 更新商品活动，通过商品id
     * @param promoItemDO  商品活动
     */
    void updateByItemIdSelective(PromoItemDO promoItemDO);

    /**
     * 通过商品id删除活动商品
     * @param id itemId
     */
    void deleteByItemId(Integer id);
}