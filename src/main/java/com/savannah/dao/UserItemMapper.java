package com.savannah.dao;

import com.savannah.dataobject.UserItemDO;

public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItemDO record);

    int insertSelective(UserItemDO record);

    UserItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserItemDO record);

    int updateByPrimaryKey(UserItemDO record);
}