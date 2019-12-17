package com.savannah.dao;

import com.savannah.dataobject.UserInfoDO;
import com.savannah.service.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stalern
 * @date 2019年12月9日17:21:32
 */
@Repository
@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoDO record);

    /**
     * 插入用户部分可选值
     * @param record 用户信息
     * @return 自增主键
     */
    int insertSelective(UserInfoDO record);

    /**
     * 通过主键获得用户信息
     * @param id 主键
     * @return 用户信息
     */
    UserInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoDO record);

    int updateByPrimaryKey(UserInfoDO record);

    /**
     * 通过email获取用户信息
     * @param email 唯一
     * @return 用户信息
     */
    UserInfoDO selectByEmail(String email);

    /**
     * 列出所有用户信息
     * @return list
     */
    @Select("select i.* , r.role from user_info i inner join user_role r on i.id = r.user_id order by i.id desc")
    List<UserDTO> listUserInfo();
}