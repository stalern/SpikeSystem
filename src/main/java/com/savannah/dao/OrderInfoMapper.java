package com.savannah.dao;

import com.savannah.dataobject.OrderInfoDO;
import org.springframework.stereotype.Repository;

/**
 * @author stalern
 * @date 2019年12月9日17:21:13
 */
@Repository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfoDO record);

    int insertSelective(OrderInfoDO record);

    OrderInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfoDO record);

    int updateByPrimaryKey(OrderInfoDO record);
}