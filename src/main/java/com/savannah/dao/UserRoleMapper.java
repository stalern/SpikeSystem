package com.savannah.dao;

import com.savannah.entity.UserRoleDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleDO record);

    /**
     * 插入用户角色表
     * @param record 如果选项为空，这用数据库的默认值，不设null
     * @return 返回主键（前提是xml有设置）
     */
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