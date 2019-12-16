package com.savannah.dao;

import com.savannah.dataobject.UserRoleDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleDO record);

    int insertSelective(UserRoleDO record);

    UserRoleDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRoleDO record);

    int updateByPrimaryKey(UserRoleDO record);

    /**
     * 通过用户id查找role
     * @param id user_id
     * @return 角色集合
     */
    UserRoleDO selectByUserId(Integer id);
}