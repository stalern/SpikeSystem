package com.savannah.dao;

import com.savannah.dataobject.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:21:32
 */
@Repository
@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoDO record);

    int insertSelective(UserInfoDO record);

    UserInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoDO record);

    int updateByPrimaryKey(UserInfoDO record);

    /**
     * 通过email获取用户信息
     * @param email 唯一
     * @return 用户信息
     */
    UserInfoDO selectByEmail(String email);
}