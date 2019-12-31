package com.savannah.dao;

import com.savannah.entity.ItemCategoryDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类
 * @author stalern
 * @date 2019年12月25日10:39:35
 * Mybatis Generator
 */
@Repository
public interface ItemCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemCategoryDO record);

    /**
     * 插入商品分类表
     * @param record itemCategoryDO
     * @return 插入后的主键（需要设置）
     */
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

    /**
     * 删除该商品id所包含的分类
     * @param itemId 商品id
     */
    void deleteByItemId(Integer itemId);

    /**
     * 拿到分类的商品
     * @param id categoryId
     * @return list
     */
    List<ItemCategoryDO> selectByCategoryId(Integer id);

    /**
     * 删除该分类所具有的商品关联
     * @param id categoryId
     */
    void deleteByCategoryId(Integer id);
}