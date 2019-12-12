package com.savannah.dao;

import com.savannah.dataobject.UserPwdDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:22:00
 */
@Repository
@Mapper
public interface UserPwdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPwdDO record);

    /**
     * 如果不为null时插入密码
     * 为了防止数据库产生null值
     * @param record record
     * @return 自增主键值
     */
    int insertSelective(UserPwdDO record);

    UserPwdDO selectByPrimaryKey(Integer id);

    /**
     * 通过用户名查找密码
     * @param userId userId
     * @return 密码
     */
    UserPwdDO selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPwdDO record);

    int updateByPrimaryKey(UserPwdDO record);
}