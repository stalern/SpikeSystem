package com.savannah.dao;

import com.savannah.entity.UserItemDO;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月27日22:49:35
 * Mybatis generate
 */
@Repository
public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItemDO record);

    /**
     * 创建用户商品
     * @param record userItem
     * @return 主键
     */
    int insertSelective(UserItemDO record);

    UserItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserItemDO record);

    int updateByPrimaryKey(UserItemDO record);

    /**
     * 删除用户商品
     * @param userId userId
     * @param itemId itemId
     */
    void deleteByUserItem(Integer userId, Integer itemId);
}