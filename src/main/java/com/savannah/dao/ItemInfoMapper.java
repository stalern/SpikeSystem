package com.savannah.dao;

import com.savannah.entity.ItemInfoDO;
import com.savannah.service.model.ItemDTO;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stalern
 * @date 2019年12月9日17:20:36
 */
@Repository
public interface ItemInfoMapper {

    /**
     * 通过商品Id删除商品
     * @param id 商品主键
     * @return 删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    int insert(ItemInfoDO record);

    /**
     * 插入商品信息
     * @param record itemInfo
     * @return 插入后的主键
     */
    int insertSelective(ItemInfoDO record);

    /**
     * 通过主键获得商品信息
     * @param id 主键
     * @return 商品信息(不包括库存，参加活动)
     */
    ItemInfoDO selectByPrimaryKey(Integer id);

    /**
     * 更新商品
     * @param record 商品信息
     * @return 更新的行数
     */
    int updateByPrimaryKeySelective(ItemInfoDO record);

    int updateByPrimaryKey(ItemInfoDO record);

    /**
     * 增加销量，这里其实可以用mybatis自带的updateSelective
     * 该返回值方式比for update方式好很多
     * @param itemId 商品id
     * @param amount 数量
     * @return 影响的条目数，成功为1，失败为0
     */
    @Update("update item_info set sales = #{amount} + sales where item_id = #{itemId}")
    int increaseSales(Integer itemId, Integer amount);

    /**
     * 通过分类id拿到商品信息
     * @param id 分类id
     * @return 商品信息
     */
    List<ItemDTO> listItemByCategoryId(Integer id);

    /**
     * 通过活动id拿到商品信息
     * @param id promoId
     * @return 商品信息
     */
    List<ItemDTO> listItemByPromoId(Integer id);
}